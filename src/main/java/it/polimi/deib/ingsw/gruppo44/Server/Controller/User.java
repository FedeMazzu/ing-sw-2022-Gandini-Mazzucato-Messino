package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

/**
 * class to manage the User
 * @author filippogandini
 */
public class User implements Serializable {
    private Player player;
    transient private Socket socket;
    //private Magician magician;
    transient private ObjectOutputStream oos;
    transient private ObjectInputStream ois;
    //private int port;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * releases the resources
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        ois.close();
        oos.close();
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
