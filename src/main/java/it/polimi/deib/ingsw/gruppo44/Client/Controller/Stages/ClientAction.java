package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.MovingStudentsMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * class to manage the Client Action
 * @author filippogandini
 */
public class ClientAction implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private int turnNumber;

    public ClientAction(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Waiting for your turn to move");
        int counter = turnNumber;
        while(counter>0){
            System.out.println("Counter: "+counter);
            //receive the outputs of the turnNumber players before you
            //there will be 6 outputs per turn

            //after moving the students
            SchoolData schoolData = (SchoolData)ois.readObject();
            IslandsData islandsData = (IslandsData)ois.readObject();
            for(Color color: Color.values()) System.out.println("Color "+color+": "+schoolData.getEntranceStudentsNum(color));
            for(int i = 0;i<12;i++){
                //if(islandsData.findGroup(i) != -1) continue;
                System.out.println("Island "+i);
                for(Color color: Color.values()){
                    System.out.println(color+": "+islandsData.getStudentsNum(i,color));
                }
            }

            //after moving motherNature
            islandsData = (IslandsData)ois.readObject();
            int motherNaturePos = ois.readInt();

            //after choosing cloud
            CloudsData cloudsData = (CloudsData)ois.readObject();
            schoolData = (SchoolData)ois.readObject();

            counter--;
        }



        //printing the actual data (we could read it from schoolData)
        System.out.println(ois.readObject());
        //sending where to move the students
        MovingStudentsMESSAGE msm = new MovingStudentsMESSAGE(clientController.getGameMode().getCloudStudents());
        msm.moveStudents();
        oos.writeObject(msm);
        oos.flush();

        //MOTHER NATURE
        System.out.println(ois.readObject());
        //sending the number of mother nature steps
        oos.writeInt(sc.nextInt());
        oos.flush();

        //CLOUDS
        System.out.println(ois.readObject());
        System.out.println("Choose a Cloud:");
        oos.writeInt(sc.nextInt());
        oos.flush();

        //RECEIVING THE INFORMATION ABOUT THE NEXT STAGE
        boolean gameEnded = ois.readBoolean();
        if (gameEnded){
            clientController.setClientStage(ClientStage.ClientEND);
        }else{
            clientController.setClientStage(ClientStage.ClientPLANNING);
        }



        //wait for others to play their turn
        counter = turnNumber + 1;
        int numberOfPlayers = clientController.getGameMode().getTeamPlayers() * clientController.getGameMode().getTeamsNumber();
        while(numberOfPlayers - counter>0){
            //receive the outputs of the turnNumber players after you
            //there will be 6 outputs per turn

            //after moving the students
            SchoolData schoolData = (SchoolData)ois.readObject();
            IslandsData islandsData = (IslandsData)ois.readObject();

            //after moving motherNature
            islandsData = (IslandsData) ois.readObject();
            int motherNaturePos = ois.readInt();

            //after choosing cloud
            CloudsData cloudsData = (CloudsData)ois.readObject();
            schoolData = (SchoolData)ois.readObject();

            counter++;
        }
    }
}
