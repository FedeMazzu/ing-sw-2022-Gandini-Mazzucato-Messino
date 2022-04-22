package it.polimi.deib.ingsw.gruppo44.Server.Controller;


import it.polimi.deib.ingsw.gruppo44.Server.Model.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TurnHandler {

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
            Player p = turnOrder.poll().player;
            cardOrder.add(p);
        }
        catch (NullPointerException e){
            e.printStackTrace(); //ç da modificare con qualcosa
        }
    }



    public PriorityQueue<Ticket> getTurnOrder(){
        return turnOrder;
    }

    public ArrayDeque<Player> getCardOrder(){
        return cardOrder;
    }

}

class TicketComparator implements Comparator<Ticket>{
    //attenzione a quale deve essere l'ordine, sicuro 99% cosi` e` corretto ç
    public int compare(Ticket t1, Ticket t2){
        if(t1.priority > t2.priority) return 1;
        else if(t1.priority < t2.priority) return -1;
        return 0;
    }

}

class Ticket {
    Player player;
    int priority;

    public Ticket(Player p, int prio){
        player = p;
        priority = prio;
    }


}
