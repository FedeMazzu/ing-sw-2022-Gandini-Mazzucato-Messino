package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.MovingStudentsMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

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

    public ClientAction(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Waiting for your turn to move");
        //printing the actual data
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
    }
}
