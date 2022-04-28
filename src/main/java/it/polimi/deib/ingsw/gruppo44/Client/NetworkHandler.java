package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.*;
import java.net.Socket;
import java.util.Map;
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
    private Scanner sc = new Scanner(System.in);

    public NetworkHandler(){
        // we pass the Ip and the port of the server we want to connect (that we ask the user)
        try { //open a socket
            socket = new Socket("127.0.0.1", SERVERPORT);
            System.out.println("Connection established.");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            clientController = new ClientController(ois,oos);
            new Thread(clientController).start();
            // remember to close the socket
        }catch (IOException ioe){
            System.out.println("Server not found");
            System.exit(0);
        }
    }



    public static void main(String[] args) {
        NetworkHandler networkHandler = new NetworkHandler();
    }
}
