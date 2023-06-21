package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.GameDataCLI;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to manage the standard Client Action
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
        System.out.println("------------------------------------------------------------------------------------------\n" +
                "------------------------------------------------------------------------------------------\n" +
                "ACTION PHASE\n" +
                "--------------------------");
        //printing the actual data (we can read it from Data)
        MessagesMethodsCLI.printData();
        //sending where to move the students (and receiving updates from the server)
        MessagesMethodsCLI.moveStudents();

        //MOTHER NATURE
        int maxMNSteps = (int)Math.ceil(((double)clientController.getLastCardSelected()/2.0));
        //sending the number of mother nature steps
        int stepsChoice;
        do {
            System.out.println("How many steps do you want mother nature to move? MAX " + maxMNSteps);
            stepsChoice = sc.nextInt();
        }while(stepsChoice<1 || stepsChoice>maxMNSteps);
        //sending the number of mother nature steps
        oos.writeInt(stepsChoice);
        oos.flush();
        MessagesMethodsCLI.receiveMotherNaturePos();

        //RECEIVING THE INFORMATION ABOUT THE END OF THE GAME
        boolean gameEnded = ois.readBoolean();
        if (gameEnded){
            clientController.setClientStage(ClientStage.CLIENT_END);
            return;
        }

        //CLOUDS
        //printing the clouds
        CloudsData cd = gameDataCLI.getCloudsData();
        List<Integer> availableCloudsId = new ArrayList<>();
        for(int i=0; i<clientController.getGameMode().getCloudsNumber();i++){
            if(!cd.isEmpty(i)){
                availableCloudsId.add(i);
                System.out.println("Cloud "+i+": ");
                for(Color color: Color.values()){
                    System.out.print(color+" "+cd.getStudentsNum(i,color)+"| ");
                }
                System.out.println();
            }
        }
        int cloudChoice;
        do {
            System.out.println("Choose a Cloud:");
            cloudChoice= sc.nextInt();
        }while(!availableCloudsId.contains(cloudChoice));

        oos.writeInt(cloudChoice);
        oos.flush();
        MessagesMethodsCLI.receiveCloudsUpdated();
        MessagesMethodsCLI.receiveSchoolsUpdated();

        clientController.setClientStage(ClientStage.WAITING_AFTER_TURN);
    }


}
