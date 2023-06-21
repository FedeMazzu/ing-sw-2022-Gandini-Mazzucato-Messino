package it.polimi.deib.ingsw.gruppo44.Server.Controller;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Class to manage the playing order
 */
public class TurnHandler implements Serializable {

    private final PriorityQueue<Ticket> turnOrder;
    private final ArrayDeque<Player> cardOrder;

    public TurnHandler(){
        turnOrder = new PriorityQueue<>(new TicketComparator());
        cardOrder = new ArrayDeque<>();
    }

    /**
     * when a player has ended their turn they get removed from the PriorityQ and get added to the Deque for the next round
     */

    public void endOfTurn(){
        try{
            cardOrder.add(Objects.requireNonNull(turnOrder.poll()).getPlayer());
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }



    public PriorityQueue<Ticket> getTurnOrder(){
        return turnOrder;
    }

    public ArrayDeque<Player> getCardOrder(){
        return cardOrder;
    }

}

class TicketComparator implements Comparator<Ticket>,Serializable{
    public int compare(Ticket t1, Ticket t2){
        if(t1.getPriority() > t2.getPriority()) return 1;
        else if(t1.getPriority() < t2.getPriority()) return -1;
        return 0;
    }

}

