package it.polimi.deib.ingsw.gruppo44.Client.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.School;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * class to contain the static methods useful for the communication protocol
 * OF THE WAITING CLIENT
 * @author
 */
public class MessagesMethods {
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static ClientController clientController;


    /**
     * method called by the WaitingClient ( before and after) to get updates in a standard turn
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static boolean standardWait() throws IOException, ClassNotFoundException {
        //receive the outputs of the turnNumber players after you
        //there will be 6 outputs per turn

        //after moving the students
        SchoolData schoolData = (SchoolData)ois.readObject();
        IslandsData islandsData = (IslandsData)ois.readObject();
        System.out.println("A player has moved the students!");
        //TEMPORARY
        printSchoolUpdated(schoolData);
        printIslandsUpdated(islandsData);

        //after moving motherNature
        islandsData = (IslandsData) ois.readObject();
        int motherNaturePos = ois.readInt();
        System.out.println("A player has moved mother nature on the island: "+ motherNaturePos+"!");

        //RECEIVING THE INFORMATION ABOUT THE END OF THE TURN
        boolean gameEnd = ois.readBoolean();
        if (gameEnd){
            clientController.setClientStage(ClientStage.ClientEND);
            return gameEnd;
        }

        //after choosing cloud
        CloudsData cloudsData = (CloudsData)ois.readObject();
        schoolData = (SchoolData)ois.readObject();
        System.out.println("A player has chosen a cloud!");
        //TEMPORARY
        printSchoolUpdated(schoolData);
        return false;
    }

    /**
     * prints a representation of the passed SchoolData
     * @param schoolData
     */
    public static void printSchoolUpdated(SchoolData schoolData) {
        System.out.println("School of the "+schoolData.getMagician()+" updated: ");
        System.out.print("Entrance: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
        System.out.print("Hall: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getHallStudentsNum(color)+" | ");
        System.out.println();
    }

    /**
     * prints a representation of the passed IslandsData
     * @param islandsData
     */
    public static void printIslandsUpdated(IslandsData islandsData) {
        System.out.println("Islands updated:");
        for(int i = 0;i<12;i++){
            if(islandsData.findGroup(i) != i) continue;
            System.out.print("Island "+i+"->|");
            for(Color color: Color.values()){
                System.out.print(color+": "+islandsData.getStudentsNum(i,color)+" | ");
            }
            System.out.println();
        }
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
            case 3:
                return characterWait3();
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

    /**
     * handles the waiting while the MovingClient is using the Character3
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static boolean characterWait3() throws IOException, ClassNotFoundException {
        IslandsData islandsData = (IslandsData) ois.readObject();
        //print islands for debug
        printIslandsUpdated(islandsData);
        boolean gameEnd = ois.readBoolean();
        if(gameEnd){
            clientController.setClientStage(ClientStage.ClientEND);
            return gameEnd;
        }
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait6() throws IOException, ClassNotFoundException {
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait8() throws IOException, ClassNotFoundException {
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait9() throws IOException, ClassNotFoundException {
        Color colorChosen = (Color) ois.readObject();
        System.out.println("The player chose: "+colorChosen);
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait10() throws IOException, ClassNotFoundException {
        SchoolData schoolData = (SchoolData) ois.readObject();
        printSchoolUpdated(schoolData);
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
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
            printSchoolUpdated(schoolData);
        }

        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) ois.readObject();
        System.out.println(updatedPrices);
        //continuing with the standard wait
        return MessagesMethods.standardWait();
    }


}
