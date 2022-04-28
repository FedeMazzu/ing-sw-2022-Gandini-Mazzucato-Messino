package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private String serverIp;
    private Scanner sc = new Scanner(System.in);

    public NetworkHandler(){
        // we pass the Ip and the port of the server we want to connect (that we ask the user)
        try { //open a socket
            serverIp = askServerIP();
            socket = new Socket(serverIp, SERVERPORT);
            superviseConnection();
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

    /**
     * chekcs if the connection persists
     */
    private void superviseConnection(){
        new Thread(()-> {
            try {
                InetAddress serverAddress = InetAddress.getByName(serverIp);
                //note that this always work if you run the server in the same machine of the client
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




    private String askServerIP(){
        String IP;
        boolean correct;
        while(true) {
            System.out.print("Enter your IP address:\n(convention xxx.xxx.xxx.xxx) -> ");
            IP = sc.next();
            correct=false;
            if(IP.length() == 15){
                correct = true;
                for(int i=0; i<15; i++){
                    if(i==3 || i==7 || i==11){
                        if(IP.charAt(i)!='.') {
                            correct = false;
                        }
                    }else if(IP.charAt(i)<'0' || IP.charAt(i)>'9'){
                        correct = false;
                    }
                }
            }
            if(correct) return IP;
            else System.out.println("Incorrect IP, try again..");

        }
    }


    public static void main(String[] args) {
        NetworkHandler networkHandler = new NetworkHandler();
    }
}
