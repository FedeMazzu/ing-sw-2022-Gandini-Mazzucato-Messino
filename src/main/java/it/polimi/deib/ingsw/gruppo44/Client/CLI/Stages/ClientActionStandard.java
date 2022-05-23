package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.GameDataCLI;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.MessagesMethodsGUI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * class to manage the Client Action
 * @author filippogandini
 */
public class ClientActionStandard implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private int turnNumber;
    private GameDataCLI gameDataCLI;

    public ClientActionStandard(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
        gameDataCLI = clientController.getGameDataCLI();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("it's your turn to move");
        //printing the actual data (we can read it from Data)
        MessagesMethodsCLI.printData();
        //sending where to move the students (and receiving updates from the server)
        MessagesMethodsCLI.moveStudents();

        //MOTHER NATURE
        System.out.println("How many steps do you want mother nature to move? MAX "+(int)Math.ceil(((double)clientController.getLastCardSelected()/2.0)));
        //sending the number of mother nature steps
        oos.writeInt(sc.nextInt());
        oos.flush();
        MessagesMethodsCLI.receiveMotherNaturePos();

        //RECEIVING THE INFORMATION ABOUT THE END OF THE GAME
        boolean gameEnded = ois.readBoolean();
        if (gameEnded){
            clientController.setClientStage(ClientStage.ClientEND);
            return;
        }

        //CLOUDS
        //printing the clouds
        CloudsData cd = gameDataCLI.getCloudsData();
        for(int i=0; i<clientController.getGameMode().getCloudsNumber();i++){
            if(!cd.isEmpty(i)){
                System.out.println("Cloud "+i+": ");
                for(Color color: Color.values()){
                    System.out.print(color+" "+cd.getStudentsNum(i,color)+"| ");
                }
                System.out.println();
            }
        }
        System.out.println("Choose a Cloud:");
        oos.writeInt(sc.nextInt());
        oos.flush();
        MessagesMethodsCLI.receiveCloudsUpdated();
        MessagesMethodsCLI.receiveSchoolsUpdated();

        clientController.setClientStage(ClientStage.WaitingAfterTurn);
    }


}
