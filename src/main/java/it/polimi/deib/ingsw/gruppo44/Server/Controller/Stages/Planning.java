package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.*;
import java.util.*;

/**
 * stage to model the planning phase
 * @author
 */
public class Planning implements Stage, Serializable {
    private final GameStage gameStage = GameStage.PLANNING;
    private final GameController gameController;
    private ArrayDeque<Player> cardOrder;
    private PriorityQueue<Ticket> turnOrder;
    private Set<Integer> cardsPlayedFromOtherPlayers;
    private boolean gameSuspension;
    private GameMode gameMode;

    public Planning(GameController gameController) {
        this.gameController = gameController;
        this.cardOrder = gameController.getTurnHandler().getCardOrder();
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        cardsPlayedFromOtherPlayers = new HashSet<>();
        this.gameSuspension = false;
        gameMode = gameController.getGameMode();
    }

    @Override
    public void handle() throws IOException, InterruptedException {

        System.out.println("-----------PLANNING PHASE----------------");
        if(gameController.isLoadedGame()){
            gameController.setLoadedGame(true);
            //handle the reconnection of other players
            //send the magicians that were chosen in the game
            //wait for the player to select magician and go on with the planning it should work as a normal game
            //we will have to wait for everyone to connect before conitnuing

            //Waiting for the players to rejoin
            int numUsers = gameController.getNumUsers();
            while(numUsers < gameMode.getTeamPlayers()*gameMode.getTeamsNumber()) {
                synchronized (gameController) {
                    gameController.wait();
                }
                numUsers = gameController.getNumUsers();
            }

            gameController.setLoadedGame(false);
            System.out.println("All the players have rejoined correctly");
            //deleting the file
            File file = new File("savedGames/"+gameController.getGameName()+".ser");
            file.delete();

            //sending Data and GameMode to all to reset the game
            for(int i=0;i<gameMode.getTeamsNumber()*gameMode.getTeamPlayers();i++){
                User tempUser = gameController.getUser(i);
                ObjectOutputStream oos = tempUser.getOos();

                oos.writeObject(gameMode);
                oos.flush();

                oos.writeObject(gameController.getData());
                oos.flush();
            }

        }


        Player currPlayer;
        User currUser;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        int cardValue;

        //sending the clouds to all the users before they start playing the cards cards
        for(int i=0;i<gameController.getGameMode().getTeamsNumber()*gameController.getGameMode().getTeamPlayers();i++){
            User tempUser = gameController.getUser(i);
            oos = tempUser.getOos();
            oos.reset(); //serve
            oos.writeObject(gameController.getData().getCloudsData());
            oos.flush();
        }

        Map<Magician,Integer> playedCards = new HashMap<>();

        while(!cardOrder.isEmpty()){
            currPlayer = cardOrder.poll();
            currUser = currPlayer.getUser();
            ois = currUser.getOis();
            //send to the next player the cards already played
            oos = currUser.getOos();

            if(turnOrder.isEmpty()) {
                //only the first player broadcast their choice

                //sending false to the first player by default
                oos.writeBoolean(gameSuspension);
                oos.flush();
                oos.writeObject(playedCards);
                oos.flush();
                gameSuspension = ois.readBoolean();

                for(int i = 0; i<gameController.getNumUsers(); i++){
                    if(currUser.equals(gameController.getUser(i))) continue;
                    ObjectOutputStream tempOos = gameController.getUser(i).getOos();
                    tempOos.writeBoolean(gameSuspension);
                    tempOos.flush();
                }
            }
            else {
                oos.writeObject(playedCards);
                oos.flush();
            }


            if(gameSuspension){
                //adding the first player because he will have to choose again
                cardOrder.addFirst(currPlayer);
                for(int i = 0; i<gameController.getNumUsers(); i++){
                    gameController.getUser(i).closeSocket();
                    //because when the user will rejoin the socket will be dated
                }
                //emptying the list of users
                gameController.clearUsers();
                //we want to stop and save the game
                gameController.saveGame(gameController.getGameName());
                gameController.setEndGame(true);
                return; //if the value is -1 we want to suspend the game
            }

            //Card Choice
            cardValue = cardChoosing(currPlayer,ois,oos);
            turnOrder.add(new Ticket(currPlayer,cardValue));
            playedCards.put(currPlayer.getMagician(),cardValue);

        }
        PriorityQueue<Ticket> tempTurnOrder = new PriorityQueue<>(turnOrder);
        int turnNumber = 0;
        while(!tempTurnOrder.isEmpty()){
            Ticket ticket = tempTurnOrder.poll();
            oos = ticket.getPlayer().getUser().getOos();
            oos.writeInt(turnNumber);
            oos.flush();
            turnNumber++;
        }

        gameController.setGameStage(GameStage.ACTION);
    }

    /**
     * manages the card choice
     * @param currPlayer
     * @param ois
     * @param oos
     * @return
     * @throws IOException
     */
    private int cardChoosing(Player currPlayer, ObjectInputStream ois, ObjectOutputStream oos)throws IOException {
        List<Integer> sendingCards;
        sendingCards = currPlayer.showAvailableCards();
        int cardValue;
        for(int i=0; i< sendingCards.size() && sendingCards.size()>1;i++){
            if(cardsPlayedFromOtherPlayers.contains(sendingCards.get(i))) sendingCards.remove(i--);
        }
        //sending the available cards for std out
        //oos.writeObject("Player "+currPlayer.getMagician()+" choose a card among:\n"+sendingCards);
        //oos.flush();


        cardValue = ois.readInt();
        cardsPlayedFromOtherPlayers.add(cardValue);
        currPlayer.playCard(cardValue);


        return cardValue;

    }

}
