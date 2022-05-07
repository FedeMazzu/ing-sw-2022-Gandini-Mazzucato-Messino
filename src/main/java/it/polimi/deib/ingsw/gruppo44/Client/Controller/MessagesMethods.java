package it.polimi.deib.ingsw.gruppo44.Client.Controller;

import it.polimi.deib.ingsw.gruppo44.Client.GameData;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * class to contain the static methods useful for the communication protocol
 * @author
 */
public class MessagesMethods {
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static ClientController clientController;
    public static GameData gameData;


    /**
     * method called by the WaitingClient ( before and after) to get updates in a standard turn
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static boolean standardWait() throws IOException, ClassNotFoundException {
        //receive the outputs of the turnNumber players after you
        //there will be 6 outputs per turn

        //after moving the students
        System.out.println("A player has moved the students!");
        receiveSchoolUpdated();
        receiveIslandsUpdated();

        //after moving motherNature
        receiveMotherNaturePos();


        //RECEIVING THE INFORMATION ABOUT THE END OF THE TURN
        boolean gameEnd = ois.readBoolean();
        if (gameEnd){
            clientController.setClientStage(ClientStage.ClientEND);
            return gameEnd;
        }

        //after choosing cloud
        receiveCloudsUpdated();
        System.out.println("A player has chosen a cloud!");
        receiveSchoolUpdated();
        return false;
    }

    public static void receiveUpdatedPrices() throws IOException, ClassNotFoundException {
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        //updating GameData
        gameData.setCharacters(updatedPrices);
        System.out.println(updatedPrices);
    }

    public static void receiveCloudsUpdated() throws IOException, ClassNotFoundException {
        CloudsData cloudsData = (CloudsData)ois.readObject();
        gameData.setCloudsData(cloudsData);
    }

    public static void receiveMotherNaturePos() throws IOException, ClassNotFoundException {
        receiveIslandsUpdated();
        int motherNaturePos = ois.readInt();
        gameData.setMotherNaturePosition(motherNaturePos);
        System.out.println("A player has moved mother nature on the island: "+ motherNaturePos+"!");
    }

    /**
     * updates and prints a representation of the passed SchoolData
     */
    public static void receiveSchoolUpdated() throws IOException, ClassNotFoundException {
        SchoolData schoolData = (SchoolData)ois.readObject();
        //updating game data
        gameData.putSchoolData(schoolData.getMagician(), schoolData);

        System.out.println("School of the "+schoolData.getMagician()+" updated: ");
        System.out.print("Entrance: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
        System.out.print("Hall: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getHallStudentsNum(color)+" | ");
        System.out.println();
    }

    /**
     * updates and prints a representation of the passed IslandsData
     */
    public static void receiveIslandsUpdated() throws IOException, ClassNotFoundException {
        IslandsData islandsData =(IslandsData) ois.readObject();
        //updating GameData
        gameData.setIslandsData(islandsData);


        String currData = "Islands updated:\n";
        for(int i=0;i<12;i++){
            if(islandsData.getGroup(i)!=-1) continue;

            currData+="Island ID: "+i+" ";
            for(Color c: Color.values()){
                currData+=c+" "+islandsData.getStudentsNum(i,c)+"| ";
            }
            if(islandsData.getHasTower(i)){
                currData+="Num Towers: "+islandsData.getGroupSize(i);
            }
            currData+="\n";
        }
        System.out.println(currData);
    }

    /**
     * method called from the Moving character to print a representation of the game
     */
    public static void printData() {
        GameData gameData = clientController.getGameData();
        String currData;
        currData = "";
        for (SchoolData sd : gameData.getSchoolDataMap().values()){
            currData += "School of the magician "+sd.getMagician()+":\n";
            currData += "Money: "+sd.getPlayerMoney()+"\n";
            currData+="Entrance:";
            for(Color color: Color.values()) currData+="Color "+color+": "+sd.getEntranceStudentsNum(color)+" | ";
            currData+="\n";
            currData+="Hall:";
            for(Color color: Color.values())  currData+="Color "+color+": "+sd.getHallStudentsNum(color)+" "+sd.hasProfessor(color)+"| ";
            currData+="\n";
        }

        currData+="Islands:\n";
        IslandsData islandsData = gameData.getIslandsData();;
        for(int i=0;i<12;i++){
            if(islandsData.getGroup(i)!=-1) continue;

            currData+="Island ID: "+i+" ";
            for(Color c: Color.values()){
                currData+=c+" "+islandsData.getStudentsNum(i,c)+"| ";
            }
            if(islandsData.getHasTower(i)){
                currData+="Num Towers: "+islandsData.getGroupSize(i);
            }
            currData+="\n";
        }
        System.out.println(currData);
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
            //case 1:
            //    break;
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

        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    /**
     * handles the waiting while the MovingClient is using the Character3
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static boolean characterWait3() throws IOException, ClassNotFoundException {
        IslandsData islandsData = (IslandsData) ois.readObject();
        //print islands for debug
        receiveIslandsUpdated();
        boolean gameEnd = ois.readBoolean();
        if(gameEnd){
            clientController.setClientStage(ClientStage.ClientEND);
            return gameEnd;
        }
        //getting updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait4() throws IOException, ClassNotFoundException {
        //getting updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait6() throws IOException, ClassNotFoundException {
        //getting updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait8() throws IOException, ClassNotFoundException {
        //getting updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait9() throws IOException, ClassNotFoundException {
        Color colorChosen = (Color) ois.readObject();
        System.out.println("The player chose: "+colorChosen);
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait10() throws IOException, ClassNotFoundException {
        SchoolData schoolData = (SchoolData) ois.readObject();
        receiveSchoolUpdated();
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }
    /**
     * handles the waiting while the MovingClient is using the Character12
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static boolean characterWait12() throws IOException, ClassNotFoundException {

        int numOfUsers = clientController.getGameMode().getTeamPlayers()* clientController.getGameMode().getTeamsNumber();
        for(int i=0; i< numOfUsers; i++){
            SchoolData schoolData = (SchoolData) ois.readObject();
            receiveSchoolUpdated();
        }

        receiveUpdatedPrices();
        //continuing with the standard wait
        return MessagesMethods.standardWait();
    }


}