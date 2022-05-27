package it.polimi.deib.ingsw.gruppo44.Server;

import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GamesManager;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Class to manage the connections with the clients
 * @author
 */
public class ClientsHandler implements Serializable {
    private final int PORT = 59090;
    private ServerSocket serverSocket;
    private GamesManager gamesManager;
    private List<User> clients; //users connected to the server

    public ClientsHandler(){
        gamesManager = new GamesManager();
        clients = new ArrayList<>();
        try {
            this.serverSocket = new ServerSocket(PORT);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * method to handle the connections
     */
    public void handleConnections() {
        Socket socket;
        User user;
        while (true) { //keep accepting clients
            try {
                System.out.println("Waiting for connections...");
                socket = serverSocket.accept();
                user = new User(socket);
                clients.add(user);
                System.out.println("Client "+socket+" Connected!\n-------------------");
                handleClient(user);

            } catch (IOException ioe) {
                System.out.println("Connection lost!");
            }
        }
    }

    private void handleClient(User user){

        new Thread(()->{
            try {
                ObjectInputStream ois = user.getOis();
                ObjectOutputStream oos = user.getOos();
                boolean gameJoined = false; //to manage the case in which there aren't game to join
                while(!gameJoined) {
                    switch ((ClientChoice) ois.readObject()) {
                        case CreateGameCHOISE:
                            CreateGameMESSAGE createGame = (CreateGameMESSAGE) ois.readObject();
                            gamesManager.createGame(createGame.getGameName(), createGame.getGameMode(), user);
                            gameJoined = true;
                            break;
                        case JoinGameCHOISE:
                            Map<String, GameMode> openGames = gamesManager.getOpenGames();
                            oos.writeObject(openGames);
                            oos.flush();
                            if (!openGames.isEmpty()) {
                                String gameName = (String) ois.readObject();
                                gameJoined = gamesManager.joinGame(gameName, user);

                                if(!gameJoined){
                                    oos.writeBoolean(false);
                                    oos.flush();
                                }
                            }
                            break;
                        case LoadGameCHOICE:
                            List<String> loadableGames = gamesManager.getLoadableGames();
                            System.out.println(loadableGames);
                        default: //case LoadGameCHOICE
                            //
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }).start();
    }

    public static void main(String[] args) {
        ClientsHandler clientsHandler = new ClientsHandler();
        clientsHandler.handleConnections();
    }
}
