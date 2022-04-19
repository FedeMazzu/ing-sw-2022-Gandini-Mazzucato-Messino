package it.polimi.deib.ingsw.gruppo44.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Note that it can be divided into different classes
 */
public class NetworkHandler {
    private Socket socket;



    public void connection(){
        // we pass the Ip and the port of the server we want to connect (that we ask the user)
        try { //open a socket
            Socket socket = new Socket("127.0.0.1", 59090);
            //receives data
            //close the socket
        }catch (IOException ioe){

        }
    }
}
