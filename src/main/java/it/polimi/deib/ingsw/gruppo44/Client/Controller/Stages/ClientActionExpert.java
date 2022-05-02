package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * stage to manage the Expert mode when the client decides to use a character
 * @author
 */
public class ClientActionExpert implements Stage{
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private int turnNumber;

    public ClientActionExpert(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        sendCharacterChoice();
    }

    private void sendCharacterChoice() throws IOException {
        System.out.println("Which Character do you want to use?( see from the GUI)");
        oos.writeInt(sc.nextInt());
        oos.flush();
        System.out.println("Character sent correctly");
        System.exit(0);
    }
}
