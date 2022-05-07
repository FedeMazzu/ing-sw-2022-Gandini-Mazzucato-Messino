package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Client.GameData;
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
public class ClientActionStandard implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private int turnNumber;
    private GameData gameData;

    public ClientActionStandard(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
        gameData = clientController.getGameData();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("it's your turn to move");
        //printing the actual data (we can read it from Data)
        MessagesMethods.printData();
        //sending where to move the students
        MovingStudentsMESSAGE msm = new MovingStudentsMESSAGE(clientController.getGameMode().getCloudStudents());
        msm.moveStudents(gameData);
        oos.writeObject(msm);
        oos.flush();
        MessagesMethods.receiveSchoolUpdated();
        MessagesMethods.receiveIslandsUpdated();

        //MOTHER NATURE
        System.out.println(ois.readObject());
        //sending the number of mother nature steps
        oos.writeInt(sc.nextInt());
        oos.flush();
        MessagesMethods.receiveMotherNaturePos();

        //RECEIVING THE INFORMATION ABOUT THE END OF THE GAME
        boolean gameEnded = ois.readBoolean();
        if (gameEnded){
            clientController.setClientStage(ClientStage.ClientEND);
            return;
        }

        //CLOUDS
        System.out.println(ois.readObject());
        System.out.println("Choose a Cloud:");
        oos.writeInt(sc.nextInt());
        oos.flush();
        MessagesMethods.receiveCloudsUpdated();
        MessagesMethods.receiveSchoolUpdated();

        clientController.setClientStage(ClientStage.WaitingAfterTurn);
    }

}
