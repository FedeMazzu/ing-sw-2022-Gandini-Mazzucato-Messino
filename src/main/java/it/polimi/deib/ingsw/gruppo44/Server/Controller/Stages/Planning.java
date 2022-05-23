package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
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

    public Planning(GameController gameController) {
        this.gameController = gameController;
        this.cardOrder = gameController.getTurnHandler().getCardOrder();
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        cardsPlayedFromOtherPlayers = new HashSet<>();
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
            oos = currUser.getOos();
            ois = currUser.getOis();
            //send to the next player the cards already played
            oos = currUser.getOos();
            oos.writeObject(playedCards);
            oos.flush();
            //Card Choice
            cardValue = cardChoosing(currPlayer,ois,oos);
            turnOrder.add(new Ticket(currPlayer,cardValue));
            playedCards.put(currPlayer.getMagician(),cardValue);

        }
        int turnNumber = 0;
        for(Ticket ticket:turnOrder){
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
