package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

/**
 * class to manage the User
 * @author filippogandini
 */
public class User {
    private Player player;
    private Magician magician;
    private String IP;
    private int port;

    public User(Player player, String IP, int port) {
        this.player = player;
        this.magician = magician;
        this.IP = IP;
        this.port = port;
    }
}
