package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Client.GameData;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

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

        //don't need to check if the name is already sed because the magician is the identifier
        oos.writeObject(askName());
        oos.flush();
        askMagician();
        System.out.println("Magician set! Waiting for others..");

        //Receiving the data to draw the GUI
        receiveData(ois);

        clientController.setClientStage(ClientStage.ClientPLANNING);
    }

    /**
     * receives the data to draw the first window
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void receiveData(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Data data = (Data)ois.readObject();

        //setting the client game data to build the Gui
        GameData gameData = clientController.getGameData();
        gameData.setData(data);

        if(clientController.getGameMode().isExpertMode()){
            //printing the characters
            System.out.println("Characters(Id=Price): "+data.getBoardData().getCharacters());
        }
    }


    private String askName() {
        System.out.println("Enter your nickname:");
        return sc.next();
    }

    private void askMagician() throws IOException, ClassNotFoundException {
        int choice;
        boolean correctChoice = true;
        int magicianChoice;
        Magician magician = null;
        do {
            System.out.println("Waiting for you turn to select the magician..");
            String availableMagicians = (String) ois.readObject();
            System.out.println("Select your magician(number):");
            System.out.print(availableMagicians);

            //send the chosen index corresponding to a magician
            magicianChoice = sc.nextInt();
            oos.writeInt(magicianChoice);
            oos.flush();

            //checking if the choice is accepted
            correctChoice = ois.readBoolean();
        }while (!correctChoice);

        //KING("King",1),WITCH("Witch",2),MONK("Monk",3),WIZARD("Wizard",4);
        switch (magicianChoice){
            case 1:
                magician = Magician.KING;
                break;
            case 2:
                magician = Magician.WITCH;
                break;
            case 3:
                magician = Magician.MONK;
                break;
            case 4:
                magician = Magician.WIZARD;
                break;
            default:
                System.out.println("Incorrect value");
                System.exit(0);
        }

        GameData gameData = new GameData(magician);
        clientController.setGameData(gameData);
        MessagesMethods.gameData =  gameData;
    }
}


