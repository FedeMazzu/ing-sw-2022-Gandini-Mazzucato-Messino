package it.polimi.deib.ingsw.gruppo44.Client.CLI;


import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MessagesMethodsCLI {
    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;

    public static ClientController clientController;
    public static GameDataCLI gameDataCLI;

    public static boolean standardWait() throws IOException, ClassNotFoundException {
        //receive the outputs of the turnNumber players after you

        //after moving the students
        System.out.println("A player is moving the students!");
        //receiving updates after each move
        for(int i=0;i<clientController.getGameMode().getCloudStudents();i++){
            //System.out.println("Il banano e` "+i);
            receiveSchoolsUpdated();
            receiveIslandsUpdated();
        }

        //after moving motherNature;
        receiveMotherNaturePos();



        //RECEIVING THE INFORMATION ABOUT THE END OF THE GAME
        boolean gameEnd = ois.readBoolean();
        if (gameEnd){
            String winningMagician =(String) ois.readObject();
            System.out.println(winningMagician);
            return true;
        }

        //after choosing cloud
        receiveCloudsUpdated();
        System.out.println("A player has chosen a cloud!");
        receiveSchoolsUpdated();
        return false;
    }

    public static void receiveUpdatedPrices() throws IOException, ClassNotFoundException {
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        //updating GameData
        clientController.getGameDataCLI().setCharacters(updatedPrices);
        System.out.println(updatedPrices);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void receiveCloudsUpdated() throws IOException, ClassNotFoundException {
        CloudsData cloudsData = (CloudsData)ois.readObject();
        clientController.getGameDataCLI().setCloudsData(cloudsData);
    }

    public static void receiveMotherNaturePos() throws IOException, ClassNotFoundException {
        System.out.println("Moving mother nature..");
        receiveIslandsUpdated();
        int motherNaturePos = ois.readInt();
        clientController.getGameDataCLI().setMotherNaturePosition(motherNaturePos);
        System.out.println("Mother nature was moved on island: "+ motherNaturePos+"!");
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * updates and prints a representation of the passed SchoolData
     */
    public static void receiveSchoolsUpdated() throws IOException, ClassNotFoundException {
        for(int i=0; i< clientController.getGameMode().getTeamsNumber()*clientController.getGameMode().getTeamPlayers();i++) {
            SchoolData schoolData = (SchoolData) ois.readObject();
            //updating game data
            clientController.getGameDataCLI().putSchoolData(schoolData.getMagician(), schoolData);

            System.out.println("School of the " + schoolData.getMagician() + " updated: ");
            if(clientController.getGameMode().isExpertMode()) {
                System.out.println("Money: " + schoolData.getPlayerMoney());
            }
            System.out.print("Entrance: ");
            for (Color color : Color.values())
                System.out.print("Color " + color + ": " + schoolData.getEntranceStudentsNum(color) + " | ");
            System.out.println();
            System.out.print("Hall: ");
            for (Color color : Color.values()){
                System.out.print("Color " + color + ": " + schoolData.getHallStudentsNum(color)+" ");
                if(schoolData.hasProfessor(color)){
                    System.out.print("PROF ");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

    /**
     * updates and prints a representation of the passed IslandsData
     */
    public static void receiveIslandsUpdated() throws IOException, ClassNotFoundException {
        IslandsData islandsData =(IslandsData) ois.readObject();
        //updating GameData
        clientController.getGameDataCLI().setIslandsData(islandsData);

        String currData = "Islands updated:\n";
        for(int i=0;i<12;i++){
            if(islandsData.getGroup(i)!=-1) continue;

            currData+="Island ID: "+i+" ";
            for(Color c: Color.values()){
                currData+=c+" "+islandsData.getStudentsNum(i,c)+"| ";
            }
            if(islandsData.getHasTower(i)){
                currData+="Num Towers: "+islandsData.getGroupSize(i);
                currData+= " "+islandsData.getTowerType(i);
            }
            currData+="\n";
        }
        System.out.println(currData);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * method called from the Moving character to print a representation of the game
     */

    public static void printData() {
        GameDataCLI gameDataCLI = clientController.getGameDataCLI();
        String currData;
        currData = "";
        for (SchoolData sd : gameDataCLI.getSchoolDataMap().values()){
            currData += "School of the magician "+sd.getMagician()+":\n";
            currData += "Money: "+sd.getPlayerMoney()+"\n";
            currData+="Entrance:";
            for(Color color: Color.values()) currData+="Color "+color+": "+sd.getEntranceStudentsNum(color)+" | ";
            currData+="\n";
            currData+="Hall: ";
            for (Color color : Color.values()){
                currData+="Color " + color + ": " + sd.getHallStudentsNum(color)+" ";
                if(sd.hasProfessor(color)){
                    currData+="PROF ";
                }
                currData+="|";
            }
            currData+="\n";
            currData += "........................................................................................\n";
        }


        currData+="Islands:\n";
        IslandsData islandsData = gameDataCLI.getIslandsData();;
        for(int i=0;i<12;i++){
            if(islandsData.getGroup(i)!=-1) continue;

            currData+="Island ID: "+i+" ";
            for(Color c: Color.values()){
                currData+=c+" "+islandsData.getStudentsNum(i,c)+"| ";
            }
            if(islandsData.getHasTower(i)){
                currData+="Num Towers: "+islandsData.getGroupSize(i);
                currData+= " "+islandsData.getTowerType(i);
            }
            currData+="\n";
        }
        System.out.println(currData);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * handles the waiting in the expert mode depending on the character chosen
     * @throws IOException
     * @throws ClassNotFoundException
     * @return  wether the game is ended or not
     */

    public static boolean characterWait() throws IOException, ClassNotFoundException {
        int charId = ois.readInt();
        System.out.println("Character used "+charId);
        switch (charId){
            case 2:
                return characterWait2();
            case 3:
                return characterWait3();
            case 4:
                return  characterWait4();
            case 6:
                return characterWait6();
            case 8:
                return characterWait8();
            case 9:
                return characterWait9();
            case 10:
                return characterWait10();
            case 12:
                return characterWait12();
            default:
                return false;
        }
    }

    private static boolean characterWait2() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    /**
     * handles the waiting while the MovingClient is using the Character3
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private static boolean characterWait3() throws IOException, ClassNotFoundException {
        receiveIslandsUpdated();
        boolean gameEnd =ois.readBoolean();
        if(gameEnd){
            return gameEnd;
        }
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    private static boolean characterWait4() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    private static boolean characterWait6() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    private static boolean characterWait8() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    private static boolean characterWait9() throws IOException, ClassNotFoundException {
        Color colorChosen = (Color) ois.readObject();

        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }

    private static boolean characterWait10() throws IOException, ClassNotFoundException {
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethodsCLI.standardWait();
    }
    /**
     * handles the waiting while the MovingClient is using the Character12
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private static boolean characterWait12() throws IOException, ClassNotFoundException {

        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        //continuing with the standard wait
        return MessagesMethodsCLI.standardWait();
    }

    public static void moveStudents() throws IOException, ClassNotFoundException {
        int movesNum = clientController.getGameMode().getCloudStudents();
        Scanner sc = new Scanner(System.in);
        Map<Color,Integer> studentMove;
        for(int i=0; i< movesNum; i++){
            studentMove = new HashMap<>();
            System.out.println("Choose a color: \n 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
            int colorChoice = sc.nextInt();
            Color color = null;
            switch (colorChoice){
                case 1:
                    color =  Color.GREEN;
                    break;
                case 2:
                    color =  Color.RED;
                    break;
                case 3:
                    color =  Color.YELLOW;
                    break;
                case 4:
                    color =  Color.PINK;
                    break;
                case 5:
                    color =  Color.BLUE;
                    break;
                default:
                    System.out.println("incorrect value");
                    System.exit(0);
            }
            System.out.println("Where do you want to put it? 0:ISLAND || 1:SCHOOL");
            int placeChoice = sc.nextInt();
            if(placeChoice==0){
                System.out.println("On which island?(0-11)");
                studentMove.put(color,sc.nextInt());

            }else{
                studentMove.put(color,-1);
            }
            //sending the move
            oos.writeObject(studentMove);
            oos.flush();
            //receiving updates
            receiveSchoolsUpdated();
            receiveIslandsUpdated();

        }

    }

}
