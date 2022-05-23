package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.GameDataCLI;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.MessagesMethodsGUI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
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
    private GameDataCLI gameDataCLI;
    private  boolean usingCharacter4;

    public ClientActionExpert(ClientController clientController,int charactreChoice) {
        usingCharacter4 = false;
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
        turnNumber = clientController.getTurnNumber();
        gameDataCLI = clientController.getGameDataCLI();
        currentCharacter = charactreChoice;
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        handleCharacter();
        if(endGame) clientController.setClientStage(ClientStage.ClientEND);
        else{
            System.out.println("it's your turn to move");
            //printing the actual data (we can read it from gameData)
            MessagesMethodsCLI.printData();

            //sending where to move the students (and receiving updates from the server)
            MessagesMethodsCLI.moveStudents();

            //MOTHER NATURE
            int maxMNSteps = (int)Math.ceil(((double)clientController.getLastCardSelected()/2.0));
            if(usingCharacter4){
                maxMNSteps += 2;
                usingCharacter4 = false;
            }

            System.out.println("How many steps do you want mother nature to move? MAX "+maxMNSteps);
            //sending the number of mother nature steps
            oos.writeInt(sc.nextInt());
            oos.flush();
            //receving the pos and the updated islands
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


    private void handleCharacter() throws IOException, ClassNotFoundException {
        switch (currentCharacter){
            case 1:
                break;
            case 2:
                handleCharacter2();
                break;
            case 3:
                handleCharacter3();
                break;
            case 4:
                handleCharacter4();
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
        //receiving the updated money after using a character
        MessagesMethodsCLI.receiveSchoolsUpdated();
        //receiving the updated prices
        MessagesMethodsCLI.receiveUpdatedPrices();
    }

    private void handleCharacter2() throws IOException, ClassNotFoundException {
        System.out.println("During this turn you get the professor also when you have the same amount of students");
    }

    private void handleCharacter3() throws IOException, ClassNotFoundException {
        System.out.println("Which island do you use the power on?");
        int islandChosen = sc.nextInt();
        oos.writeInt(islandChosen);
        oos.flush();
        MessagesMethodsCLI.receiveIslandsUpdated();

        endGame = ois.readBoolean();

    }

    private void handleCharacter4() {
        System.out.println("In this turn you can move mother nature up to two additional steps\n");
        usingCharacter4=true;
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

    private void handleCharacter10() throws IOException, ClassNotFoundException {
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
        MessagesMethodsCLI.receiveSchoolsUpdated();
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
