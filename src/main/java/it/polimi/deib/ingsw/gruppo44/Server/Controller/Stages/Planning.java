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

    public Planning(GameController gameController) {
        this.gameController = gameController;
        this.cardOrder = gameController.getTurnHandler().getCardOrder();
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
    }

    @Override
    public void handle() throws IOException {
        Set<Integer> cardsPlayedFromOtherPlayers = new HashSet<>();
        List<Card> sendingCards;
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
            sendingCards = currPlayer.showAvailableCards();
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
            turnOrder.add(new Ticket(currPlayer,cardValue));
        }
        gameController.setGameStage(GameStage.ACTION);
    }
}
