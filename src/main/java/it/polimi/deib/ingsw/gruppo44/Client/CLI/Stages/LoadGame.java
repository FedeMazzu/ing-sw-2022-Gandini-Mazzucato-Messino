package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * manages the load game stage
 * @author filippogandini
 */
public class LoadGame implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public LoadGame(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        //to do

    }
}
