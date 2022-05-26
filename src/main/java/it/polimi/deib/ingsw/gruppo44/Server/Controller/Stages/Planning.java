package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Card;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    public Planning(GameController gameController) {
        this.gameController = gameController;
        this.cardOrder = gameController.getTurnHandler().getCardOrder();
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        cardsPlayedFromOtherPlayers = new HashSet<>();
        this.gameSuspension = false;
    }

    @Override
    public void handle() throws IOException {

        System.out.println("-----------PLANNING PHASE----------------");
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

                oos.writeBoolean(gameSuspension);
                oos.flush();
                oos.writeObject(playedCards);
                oos.flush();
                gameSuspension = ois.readBoolean();

                for(int i=0;i<gameController.getNumUsers();i++){
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
                //we want to stop and save the game
                gameController.saveGame(gameController.getGameName());
                gameController.setEndGame(true);
                for(int i=0;i<gameController.getNumUsers();i++){
                    gameController.getUser(i).closeSocket();
                }
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
