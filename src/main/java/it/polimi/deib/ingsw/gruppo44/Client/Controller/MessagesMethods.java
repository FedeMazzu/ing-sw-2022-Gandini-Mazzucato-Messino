package it.polimi.deib.ingsw.gruppo44.Client.Controller;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.View.GameData;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * class to contain the static methods useful for the communication protocol
 * @author
 */
public class MessagesMethods {


    /**
     * method called by the WaitingClient ( before and after) to get updates in a standard turn
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static boolean standardWait() throws IOException, ClassNotFoundException {
        //receive the outputs of the turnNumber players after you

        //after moving the students
        System.out.println("A player has moved the students!");
        for(int i=0;i<Eriantys.getCurrentApplication().getGameMode().getCloudStudents();i++){
            //System.out.println("Il banano e` "+i);
            receiveSchoolUpdated();
            receiveIslandsUpdated();
        }

        //after moving motherNature
        receiveMotherNaturePos();


        //RECEIVING THE INFORMATION ABOUT THE END OF THE TURN
        boolean gameEnd = Eriantys.getCurrentApplication().getOis().readBoolean();
        if (gameEnd){
            String winningMagician =(String) Eriantys.getCurrentApplication().getOis().readObject();

            Platform.runLater(()->{
                Eriantys.getCurrentApplication().getEndGameSceneController().getWinLabel().setText(winningMagician);
            });

            Eriantys.getCurrentApplication().switchToEndGameScene();
        }

        //after choosing cloud
        receiveCloudsUpdated();
        System.out.println("A player has chosen a cloud!");
        receiveSchoolUpdated();
        return false;
    }

    public static void receiveUpdatedPrices() throws IOException, ClassNotFoundException {
        //getting updated prices
        Map<Integer,Integer> updatedPrices =(Map<Integer, Integer>) Eriantys.getCurrentApplication().getOis().readObject();
        //updating GameData
        Eriantys.getCurrentApplication().getGameData().setCharacters(updatedPrices);
        System.out.println(updatedPrices);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void receiveCloudsUpdated() throws IOException, ClassNotFoundException {
        CloudsData cloudsData = (CloudsData)Eriantys.getCurrentApplication().getOis().readObject();
        Eriantys.getCurrentApplication().getGameData().setCloudsData(cloudsData);
    }

    public static void receiveMotherNaturePos() throws IOException, ClassNotFoundException {
        System.out.println("Moving mother nature..");
        receiveIslandsUpdated();
        int motherNaturePos = Eriantys.getCurrentApplication().getOis().readInt();
        Eriantys.getCurrentApplication().getGameData().setMotherNaturePosition(motherNaturePos);
        System.out.println("A player has moved mother nature on the island: "+ motherNaturePos+"!");
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * updates and prints a representation of the passed SchoolData
     */
    public static void receiveSchoolUpdated() throws IOException, ClassNotFoundException {
        System.out.println("PRIMA OIS");
        SchoolData schoolData = (SchoolData)Eriantys.getCurrentApplication().getOis().readObject();
        System.out.println("DOPO OIS");
        //updating game data
        Eriantys.getCurrentApplication().getGameData().putSchoolData(schoolData.getMagician(), schoolData);

        System.out.println("School of the "+schoolData.getMagician()+" updated: ");
        System.out.println("Money: "+schoolData.getPlayerMoney());
        System.out.print("Entrance: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
        System.out.print("Hall: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getHallStudentsNum(color)+" "+schoolData.hasProfessor(color)+" | ");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * updates and prints a representation of the passed IslandsData
     */
    public static void receiveIslandsUpdated() throws IOException, ClassNotFoundException {
        IslandsData islandsData =(IslandsData) Eriantys.getCurrentApplication().getOis().readObject();
        //updating GameData
        Eriantys.getCurrentApplication().getGameData().setIslandsData(islandsData);


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
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * method called from the Moving character to print a representation of the game
     */

    public static void printData() {
        GameData gameData = Eriantys.getCurrentApplication().getGameData();
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
            currData += "........................................................................................\n";
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
        System.out.println("------------------------------------------------------------------------------------------");
    }

    /**
     * handles the waiting in the expert mode depending on the character chosen
     * @throws IOException
     * @throws ClassNotFoundException
     * @return  wether the game is ended or not
     */

    public static boolean characterWait() throws IOException, ClassNotFoundException {
        int charId = Eriantys.getCurrentApplication().getOis().readInt();
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
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    /**
     * handles the waiting while the MovingClient is using the Character3
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private static boolean characterWait3() throws IOException, ClassNotFoundException {
        IslandsData islandsData = (IslandsData) Eriantys.getCurrentApplication().getOis().readObject();
        //print islands for debug
        receiveIslandsUpdated();
        boolean gameEnd = Eriantys.getCurrentApplication().getOis().readBoolean();
        if(gameEnd){
            //clientController.setClientStage(ClientStage.ClientEND);
            return gameEnd;
        }
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait4() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait6() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait8() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait9() throws IOException, ClassNotFoundException {
        Color colorChosen = (Color) Eriantys.getCurrentApplication().getOis().readObject();
        System.out.println("The player chose: "+colorChosen);
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait10() throws IOException, ClassNotFoundException {
        SchoolData schoolData = (SchoolData) Eriantys.getCurrentApplication().getOis().readObject();
        receiveSchoolUpdated();
        //receiving the MovingClient updated money (it's redundant in this case)
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }
    /**
     * handles the waiting while the MovingClient is using the Character12
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private static boolean characterWait12() throws IOException, ClassNotFoundException {

        int numOfUsers = Eriantys.getCurrentApplication().getGameMode().getTeamPlayers()* Eriantys.getCurrentApplication().getGameMode().getTeamsNumber();
        for(int i=0; i< numOfUsers; i++){
            SchoolData schoolData = (SchoolData) Eriantys.getCurrentApplication().getOis().readObject();
            receiveSchoolUpdated();
        }
        //receiving the MovingClient updated money
        receiveSchoolUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        //continuing with the standard wait
        return MessagesMethods.standardWait();
    }


}
