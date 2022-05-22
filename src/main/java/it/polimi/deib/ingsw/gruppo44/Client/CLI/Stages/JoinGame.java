package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * manages the join Game stage
 * @author filippogandini
 */
public class JoinGame implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);

    public JoinGame(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        Map<String, GameMode> openGames;
        oos.writeObject(ClientChoice.JoinGameCHOISE);
        oos.flush();
        openGames = (Map<String, GameMode>) ois.readObject();
        if(!openGames.isEmpty()) {
            System.out.println("Which game do you want to join?");
            System.out.println(openGames);
            String gameChoice = sc.next();
            clientController.setGameMode(openGames.get(gameChoice));
            oos.writeObject(gameChoice);
            oos.flush();
            System.out.println("Waiting for the other players...");

            //getting a bolean indicating if the game was joined correctly
            boolean ack = getStartingAck();
            if(ack){
                clientController.setClientStage(ClientStage.SETUP);
            }
            else{
                System.out.println("There aren't available games with that name!");
                clientController.askOptions();
            }


        }else{
            System.out.println("There aren't available games!");
            clientController.askOptions();
        }

    }

    private boolean getStartingAck() throws IOException, ClassNotFoundException {
        return ois.readBoolean();
    }

}
