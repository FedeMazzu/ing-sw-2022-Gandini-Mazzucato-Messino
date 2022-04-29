package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Card;
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
        while(!cardOrder.isEmpty()){
            currPlayer = cardOrder.poll();
            currUser = currPlayer.getUser();
            oos = currUser.getOos();
            ois = currUser.getOis();
            //send to the next player the cards already played
            User tempUser = cardOrder.peek().getUser();
            oos = tempUser.getOos();
            oos.writeObject(turnOrder);
            oos.flush();
            //Card Choice
            cardValue = cardChoosing(currPlayer,ois,oos);
            turnOrder.add(new Ticket(currPlayer,cardValue));

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
        List<Card> sendingCards;
        sendingCards = currPlayer.showAvailableCards();
        int cardValue;
        System.out.println(sendingCards);
        for(int i=0; i< sendingCards.size() && sendingCards.size()>1;i++){
            if(cardsPlayedFromOtherPlayers.contains(sendingCards.get(i).getValue())) sendingCards.remove(i--);
        }
        //sending the available cards
        oos.writeObject("Player "+currPlayer.getMagician()+" choose a card among:\n"+sendingCards);
        oos.flush();
        cardValue = ois.readInt();
        cardsPlayedFromOtherPlayers.add(cardValue);
        currPlayer.playCard(cardValue);

        return cardValue;
    }
}