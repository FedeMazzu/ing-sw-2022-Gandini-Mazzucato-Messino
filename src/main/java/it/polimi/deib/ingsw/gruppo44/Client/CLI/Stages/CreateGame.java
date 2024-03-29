package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.ClientChoice;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.CreateGameMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to manage the creation of a game
 */
public class CreateGame implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);

    public CreateGame(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        oos.writeObject(ClientChoice.CreateGameCHOISE);
        oos.flush();
        CreateGameMESSAGE createGameMESSAGE = new CreateGameMESSAGE(askGameName(),askGameMode());
        oos.writeObject(createGameMESSAGE);
        oos.flush();
        System.out.println("Waiting for the other players...");
        //getting a boolean indicating if the game was created and joined correctly
        getStartingAck();
        clientController.setClientStage(ClientStage.SETUP);
    }

    private boolean getStartingAck() throws IOException, ClassNotFoundException {
        return ois.readBoolean();
    }


    private GameMode askGameMode()throws InputMismatchException {
        int a;
        do {
            System.out.println("Choose the game mode(number):\n" +
                    "1 - TwoPlayersBasic\n" +
                    "2 - TwoPlayersExpert\n" +
                    "3 - ThreePlayersBasic\n" +
                    "4 - ThreePlayersExpert\n" +
                    "5 - TeamBasic\n" +
                    "6 - TeamExpert");

            a = sc.nextInt();
        } while (a < 1 || a > 6);
        switch (a) {
            case 1:
                clientController.setGameMode(GameMode.TwoPlayersStandard);
                return GameMode.TwoPlayersStandard;
            case 2:
                clientController.setGameMode(GameMode.TwoPlayersExpert);
                return GameMode.TwoPlayersExpert;
            case 3:
                clientController.setGameMode(GameMode.ThreePlayersStandard);
                return GameMode.ThreePlayersStandard;
            case 4:
                clientController.setGameMode(GameMode.ThreePlayersExpert);
                return GameMode.ThreePlayersExpert;
            case 5:
                clientController.setGameMode(GameMode.TeamStandard);
                return GameMode.TeamStandard;
            default://else
                clientController.setGameMode(GameMode.TeamExpert);
                return GameMode.TeamExpert;
        }
    }

    private String askGameName() {
        System.out.println("Insert the game name:");
        return sc.nextLine();
    }
}
