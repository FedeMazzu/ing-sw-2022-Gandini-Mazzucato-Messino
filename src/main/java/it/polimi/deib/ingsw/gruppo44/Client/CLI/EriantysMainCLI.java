package it.polimi.deib.ingsw.gruppo44.Client.CLI;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class to start the CLI game and to manage the connection
 */

public class EriantysMainCLI {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ClientController clientController;
    private final int SERVERPORT = 59090;
    private String serverIp;
    private Scanner sc = new Scanner(System.in);
    private boolean connectionEstablished;

    public EriantysMainCLI(){
        // we pass the Ip and the port of the server we want to connect (that we ask the user)
        try { //open a socket
            serverIp = askServerIP();
            socket = new Socket(serverIp, SERVERPORT);
            connectionEstablished = true;
            superviseConnection();
            System.out.println("Connection established.");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            clientController = new ClientController(ois,oos,socket);
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

    private String askServerIP(){
        String IP;
        boolean correct;
        while(true) {
            System.out.print("Enter server Ip address:\n(convention xxx.xxx.xxx.xxx) -> ");
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
        EriantysMainCLI networkHandler = new EriantysMainCLI();
    }
}
