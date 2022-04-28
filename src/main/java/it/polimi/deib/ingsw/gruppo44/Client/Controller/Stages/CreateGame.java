package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
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
 * manages the Create Game stage
 * @author filippogandini
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
        //maybe need to check if the server receives the option
        CreateGameMESSAGE createGameMESSAGE = new CreateGameMESSAGE(askGameName(),askGameMode());
        oos.writeObject(createGameMESSAGE);
        oos.flush();

        clientController.setClientStage(ClientStage.SETUP);
        System.out.println("Waiting for the other players...");
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
                clientController.setGameMode(GameMode.TwoPlayersBasic);
                return GameMode.TwoPlayersBasic;
            case 2:
                clientController.setGameMode(GameMode.TwoPlayersExpert);
                return GameMode.TwoPlayersExpert;
            case 3:
                clientController.setGameMode(GameMode.ThreePlayersBasic);
                return GameMode.ThreePlayersBasic;
            case 4:
                clientController.setGameMode(GameMode.ThreePlayersExpert);
                return GameMode.ThreePlayersExpert;
            case 5:
                clientController.setGameMode(GameMode.TeamBasic);
                return GameMode.TeamBasic;
            default://else
                clientController.setGameMode(GameMode.TeamExpert);
                return GameMode.TeamExpert;
        }
    }

    private String askGameName() {
        System.out.println("Insert the game name:");
        return sc.next();
    }
}
