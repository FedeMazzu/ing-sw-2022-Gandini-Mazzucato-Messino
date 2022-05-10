package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Note that it can be divided into different classes
 * @author
 */

public class NetworkHandler {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ClientController clientController;
    private final int SERVERPORT = 59090;
    private String serverIp;
    private Scanner sc = new Scanner(System.in);
    private boolean connectionEstablished;

    public NetworkHandler(String serverIp){
        // we pass the Ip and the port of the server we want to connect (that we ask the user)
        try { //open a socket
            //serverIp = askServerIP();
            socket = new Socket(serverIp, SERVERPORT);
            connectionEstablished = true;
            superviseConnection();
            System.out.println("Connection established.");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            Eriantys eriantys = Eriantys.getCurrentApplication();
            eriantys.setOis(ois);
            eriantys.setOos(oos);
            clientController = new ClientController(ois,oos);
            new Thread(clientController).start();
            // remember to close the socket
        }catch (IOException ioe){
            connectionEstablished = false;
            System.out.println("Server not found");
        }
    }

    /**
     * checks if the connection persists
     */
    private void superviseConnection() {
        new Thread(() -> {
            try {
                InetAddress serverAddress = InetAddress.getByName(serverIp);
                //note that this always work if you run the server on the same machine of the client
                while (serverAddress.isReachable(5000)) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Connection Lost");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }).start();
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }

    public static void main(String[] args) {
        Eriantys.main(args);
       // NetworkHandler networkHandler = new NetworkHandler();
    }
}
