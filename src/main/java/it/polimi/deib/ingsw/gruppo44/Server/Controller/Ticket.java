package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.Serializable;

public class Ticket implements Serializable {
    private Player player;
    private int priority;

    public Ticket(Player p, int prio) {
        player = p;
        priority = prio;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPriority() {
        return priority;
    }
}
