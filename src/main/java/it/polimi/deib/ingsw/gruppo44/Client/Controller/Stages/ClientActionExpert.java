package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Messages.MovingStudentsMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

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

    private void handleCharacter() throws IOException, ClassNotFoundException {
        switch (currentCharacter){
            case 1:
                break;
            case 2:
                break;
            case 3:
                handleCharacter3();
                break;
            case 6:
                handleCharacter6();
                break;
            case 8:
                handleCharacter8();
                break;
            case 9:
                handleCharacter9();
                break;
            case 10:
                handleCharacter10();
                break;
            case 12:
                handleCharacter12();
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

    private void handleCharacter6(){
        System.out.println("Towers do not count towards influence score this turn!");
    }

    private void handleCharacter8(){
        System.out.println("Your influence score will be 2 points higher this turn!");
    }

    private void handleCharacter9() throws IOException {

        System.out.println("Select a color to apply the effect:");
        System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
        int colorChoice = sc.nextInt();
        switch (colorChoice){
            case 1:
                oos.writeObject(Color.GREEN);
                oos.flush();
                break;
            case 2:
                oos.writeObject(Color.RED);
                oos.flush();
                break;
            case 3:
                oos.writeObject(Color.YELLOW);
                oos.flush();
                break;
            case 4:
                oos.writeObject(Color.PINK);
                oos.flush();
                break;
            case 5:
                oos.writeObject(Color.BLUE);
                oos.flush();
                break;
            default:
                System.out.println("you choose "+colorChoice );
                System.out.println("incorrect value");
                System.exit(0);
                break;
        }

    }

    private void handleCharacter10() throws IOException {
        System.out.println("You want to swap 2 students in your school?\n" +
                "0 -> NO\n 1 -> YES");
        int swapChoice = sc.nextInt();
        if(swapChoice == 0){
            oos.writeBoolean(false);
            oos.flush();
            return;
        }
        else{
            oos.writeBoolean(true);
            oos.flush();
            System.out.println("Which 2 colors to swap? (First Hall then Entrance)");
            System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
            int [] colorChoices = new int[2];
            colorChoices[0] = sc.nextInt(); //Hall Student Color
            colorChoices[1] = sc.nextInt(); //Entrance Student Color
            for(int i=0;i<2;i++){
                switch (colorChoices[i]){
                    case 1:
                        oos.writeObject(Color.GREEN);
                        oos.flush();
                        break;
                    case 2:
                        oos.writeObject(Color.RED);
                        oos.flush();
                        break;
                    case 3:
                        oos.writeObject(Color.YELLOW);
                        oos.flush();
                        break;
                    case 4:
                        oos.writeObject(Color.PINK);
                        oos.flush();
                        break;
                    case 5:
                        oos.writeObject(Color.BLUE);
                        oos.flush();
                        break;
                    default:
                        System.out.println("incorrect value");
                        System.exit(0);
                        break;
                }
            }
            System.out.println("You want to swap 2 students in your school?\n" +
                    "0 -> NO\n 1 -> YES");
            swapChoice = sc.nextInt();
            if(swapChoice == 0){
                oos.writeBoolean(false);
                oos.flush();
                return;
            }
            else{
                oos.writeBoolean(true);
                oos.flush();
                System.out.println("Which 2 colors to swap? (First Hall then Entrance)");
                System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
                colorChoices[0] = sc.nextInt(); //Hall Student Color
                colorChoices[1] = sc.nextInt(); //Entrance Student Color
                for(int i=0;i<2;i++){
                    switch (colorChoices[i]){
                        case 1:
                            oos.writeObject(Color.GREEN);
                            oos.flush();
                            break;
                        case 2:
                            oos.writeObject(Color.RED);
                            oos.flush();
                            break;
                        case 3:
                            oos.writeObject(Color.YELLOW);
                            oos.flush();
                            break;
                        case 4:
                            oos.writeObject(Color.PINK);
                            oos.flush();
                            break;
                        case 5:
                            oos.writeObject(Color.BLUE);
                            oos.flush();
                            break;
                        default:
                            System.out.println("incorrect value");
                            System.exit(0);
                            break;
                    }
                }

            }

        }
    }

    private void handleCharacter12() throws IOException, ClassNotFoundException {
        System.out.println("Select a color to apply the effect:");
        System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
        int colorChoice = sc.nextInt();
        switch (colorChoice){
            case 1:
                oos.writeObject(Color.GREEN);
                oos.flush();
                break;
            case 2:
                oos.writeObject(Color.RED);
                oos.flush();
                break;
            case 3:
                oos.writeObject(Color.YELLOW);
                oos.flush();
                break;
            case 4:
                oos.writeObject(Color.PINK);
                oos.flush();
                break;
            case 5:
                oos.writeObject(Color.BLUE);
                oos.flush();
                break;
            default:
                System.out.println("you choose "+colorChoice );
                System.out.println("incorrect value");
                System.exit(0);
                break;
        }
        int numOfUsers = clientController.getGameMode().getTeamPlayers()* clientController.getGameMode().getTeamsNumber();
        for(int i=0; i< numOfUsers; i++){
            SchoolData schoolData = (SchoolData) ois.readObject();
            System.out.println("HALL of the School of the player:"+schoolData.getMagician()+":");
            for(Color c: Color.values()){
                System.out.print(c+" "+schoolData.getHallStudentsNum(c)+"| ");
            }
            System.out.println();
        }

    }
}
