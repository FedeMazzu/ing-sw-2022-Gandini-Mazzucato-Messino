package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * stage to manage the client who has already moved and is waiting the end of the other palyers turn
 * and, in the meanwhile, is receiving the updates of the moves made by the player who's moving
 * @author filippogandini
 */
public class WaitingAfterTurn implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private int turnNumber;

    public WaitingAfterTurn(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        turnNumber = clientController.getTurnNumber();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        int counter = turnNumber + 1;
        int numberOfPlayers = clientController.getGameMode().getTeamPlayers() * clientController.getGameMode().getTeamsNumber();
        while(numberOfPlayers - counter>0){
            System.out.println("Waiting, another player is moving...");
            //receive the outputs of the turnNumber players after you
            //there will be 6 outputs per turn

            //after moving the students
            SchoolData schoolData = (SchoolData)ois.readObject();
            IslandsData islandsData = (IslandsData)ois.readObject();
            System.out.println("A player has moved the students!");
            //TEMPORARY
            printSchoolAndIslandsUpdated(schoolData,islandsData);

            //after moving motherNature
            islandsData = (IslandsData) ois.readObject();
            int motherNaturePos = ois.readInt();
            System.out.println("A player has moved mother nature on the island: "+ motherNaturePos+"!");

            //RECEIVING THE INFORMATION ABOUT THE END OF THE TURN
            boolean gameEnded = ois.readBoolean();
            if (gameEnded){
                clientController.setClientStage(ClientStage.ClientEND);
                return;
            }

            //after choosing cloud
            CloudsData cloudsData = (CloudsData)ois.readObject();
            schoolData = (SchoolData)ois.readObject();
            System.out.println("A player has chosen a cloud!");
            //TEMPORARY
            printSchoolUpdated(schoolData);

            counter++;
        }

        clientController.setClientStage(ClientStage.ClientPLANNING);
    }

    private void printSchoolUpdated(SchoolData schoolData) {
        System.out.print("School updated: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
    }

    private void printSchoolAndIslandsUpdated(SchoolData schoolData,IslandsData islandsData) {
        System.out.print("School updated: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
        for(int i = 0;i<12;i++){
            if(islandsData.findGroup(i) != i) continue;
            System.out.println("Island "+i);
            for(Color color: Color.values()){
                System.out.print(color+": "+islandsData.getStudentsNum(i,color)+" | ");
            }
            System.out.println();
        }
    }

}
