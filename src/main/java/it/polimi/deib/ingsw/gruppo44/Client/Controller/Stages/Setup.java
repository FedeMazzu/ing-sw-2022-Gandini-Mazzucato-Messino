package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * manages the setup stage
 * @author filippogandini
 */
public class Setup implements Stage {
    private ClientController  clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc  = new Scanner(System.in);

    public Setup(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {

        //need to check if the name isn't already used
        oos.writeObject(askName());
        oos.flush();
        askMagician();
        System.out.println("Magician set! Waiting for others..");

        //Receiving the data to draw the GUI
        Data data = (Data)ois.readObject();
        clientController.setClientStage(ClientStage.ClientPLANNING);
    }



    private String askName() {
        System.out.println("Enter your nickname:");
        return sc.next();
    }

    private void askMagician() throws IOException, ClassNotFoundException {
        int choice;
        boolean correctChoice = true;
        do {
            System.out.println("Waiting for you turn to select the magician..");
            String availableMagicians = (String) ois.readObject();
            System.out.println("Select your magician(number):");
            System.out.print(availableMagicians);

            //send the chosen index corresponding to a magician
            oos.writeInt(sc.nextInt());
            oos.flush();

            //checking if the choice is accepted
            correctChoice = ois.readBoolean();
        }while (!correctChoice);
    }
}


