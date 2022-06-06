package it.polimi.deib.ingsw.gruppo44.Server.Controller;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class to manage the playing order
 */
public class TurnHandler implements Serializable {

    private PriorityQueue<Ticket> turnOrder;
    private ArrayDeque<Player> cardOrder;
    private Player currPlayer;
    private int numOfPlayers;

    public TurnHandler(GameMode gameMode){
        turnOrder = new PriorityQueue<Ticket>(new TicketComparator());
        cardOrder = new ArrayDeque<Player>();
        numOfPlayers = gameMode.getTeamPlayers() * gameMode.getTeamsNumber();
    }

    /**
     * when a player has ended their turn they get removed from the PrioQ and get added to the Deque for the next round
     */

    public void endOfTurn(){
        try{
            Player p = turnOrder.poll().getPlayer();
            cardOrder.add(p);
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

