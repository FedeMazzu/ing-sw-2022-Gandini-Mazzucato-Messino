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
 * stage to manage the Expert mode when the client decides to use a character
 * @author
 */
public class ClientActionExpert implements Stage{
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);
    private int turnNumber;
    private int currentCharacter;
    private boolean endGame;

    public ClientActionExpert(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        sendCharacterChoice();
        handleCharacter();
        if(endGame) clientController.setClientStage(ClientStage.ClientEND);
        else{
            System.out.println("it's your turn to move");
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

            clientController.setClientStage(ClientStage.WaitingAfterTurn);
        }

    }

    private void sendCharacterChoice() throws IOException {
        System.out.println("Which Character do you want to use?( see from the GUI)");
        currentCharacter = sc.nextInt();
        oos.writeInt(currentCharacter);
        oos.flush();
        System.out.println("Character sent correctly");

    }

    private void handleCharacter() throws IOException {
        switch (currentCharacter){
            case 1:
                break;
            case 2:
                break;
            case 3:
                handleCharacter3();
                break;
        }
    }

    private void handleCharacter3() throws IOException {
        System.out.println("Which island do you use the power on?");
        int islandChosen = sc.nextInt();
        oos.writeInt(islandChosen);
        oos.flush();
        System.out.println(endGame);
        endGame = ois.readBoolean();
        System.out.println(endGame);
    }
}
