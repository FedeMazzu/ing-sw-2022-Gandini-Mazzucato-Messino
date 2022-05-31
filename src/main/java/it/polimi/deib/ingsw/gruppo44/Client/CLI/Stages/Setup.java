package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.GameDataCLI;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
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
        String name = askName();
        Magician magician = askMagician();


        oos.writeObject(magician);
        oos.flush();
        oos.writeObject(name);
        oos.flush();

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
        GameDataCLI gameDataCLI = clientController.getGameDataCLI();
        gameDataCLI.setData(data);
        MessagesMethodsCLI.printData();

        if(clientController.getGameMode().isExpertMode()){
            //printing the characters
            System.out.println("Characters(Id=Price): "+data.getBoardData().getCharacters());
        }
    }


    private String askName() {
        System.out.println("Enter your nickname:");
        return sc.next();
    }

    private Magician askMagician() throws IOException, ClassNotFoundException {
        int choice;
        boolean correctChoice = true;
        int magicianChoice;
        Magician magician = null;

        System.out.println("Waiting for you turn to select the magician..");
        List<Magician> availableMagicians = (ArrayList<Magician>) ois.readObject();
        System.out.println("These are the available magicians:");
        int i=0;
        for (Magician mag: availableMagicians){
            System.out.println(i+" - "+mag);
            i++;
        }

        //send the chosen index corresponding to a magician
        do {
            System.out.println("Select your magician(number):");
            magicianChoice = sc.nextInt();
        }while(magicianChoice<0 || magicianChoice>i);

        magician = availableMagicians.get(magicianChoice);

        GameDataCLI gameDataCLI = new GameDataCLI(magician);
        clientController.setGameData(gameDataCLI);
        return magician;
        //MessagesMethods.gameData =  gameData;
    }
}


