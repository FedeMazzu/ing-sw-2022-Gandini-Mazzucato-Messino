package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.Serializable;

/**
 * class to associate a priority to a player
 */
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

    @Override
    public String toString() {
        return "Ticket{" +
                "player=" + player.getMagician() +
                ", priority=" + priority +
                '}';
    }
}
