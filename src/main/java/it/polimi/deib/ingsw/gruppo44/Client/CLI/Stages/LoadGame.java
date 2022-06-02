package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Class to manage the LoadGame
 */
public class LoadGame implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);

    public LoadGame(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        oos.writeObject(ClientChoice.LoadGameCHOICE);
        oos.flush();
        //receiving the saved Games
        List<String> savedGames = (List<String>) ois.readObject();
        if(!savedGames.isEmpty()){
            System.out.println("Select a game to load: (name)");
            System.out.println(savedGames);

            //sending the chosen game
            oos.writeObject(sc.next());
            oos.flush();

            MessagesMethodsCLI.setupToReloadGame();


        }else{
            System.out.println("There aren't available games!");
            clientController.askOptions();
        }

    }
}
