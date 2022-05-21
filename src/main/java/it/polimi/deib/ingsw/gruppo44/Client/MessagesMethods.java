package it.polimi.deib.ingsw.gruppo44.Client;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.View.GameData;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * class to contain the static methods useful for the communication protocol
 * @author
 */
public class MessagesMethods {

    /**
     * called to set the GUI for the moves
     * NOTE THAT THIS METHOD MUST DE CALLED ON THE EVENT THREAD
     */
    public static void setForMovingStudents(){
        //setting the phase attribute to "wake up" events
        Eriantys.getCurrentApplication().getIslandsSceneController().setPhase(0);
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("Move the students");});
        Rectangle rectangle = Eriantys.getCurrentApplication().getIslandsSceneController().getStudentChoicePanel();
        ListView<Color> entranceStudentsSel = Eriantys.getCurrentApplication().getIslandsSceneController().getEntranceStudentsSelection();
        Button schoolSelButton = Eriantys.getCurrentApplication().getIslandsSceneController().getSchoolSelectionButton();
        SchoolData schoolData = Eriantys.getCurrentApplication().getGameData().getSchoolDataMap().get(Eriantys.getCurrentApplication().getGameData().getClientMagician());

        for(Color color:Color.values()){
            int numOfStudents=schoolData.getEntranceStudentsNum(color);
            for(int i=0;i<numOfStudents;i++){
                entranceStudentsSel.getItems().add(color);
            }
        }

        for(int i=0;i<12;i++){
            IslandGuiLogic igl = Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().get(i);
            if(igl.isCovered()) continue;
            igl.getCircle().setVisible(true);
        }

        rectangle.setVisible(true);
        schoolSelButton.setVisible(true);
        entranceStudentsSel.setVisible(true);
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }

    /**
     * method called by the WaitingClient ( before and after) to get updates in a standard turn
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static boolean standardWait() throws IOException, ClassNotFoundException {
        //receive the outputs of the turnNumber players after you

        //after moving the students
        System.out.println("A player has moved the students!");
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("A player is moving the students");});
        for(int i=0;i<Eriantys.getCurrentApplication().getGameMode().getCloudStudents();i++){
            //System.out.println("Il banano e` "+i);
            receiveSchoolsUpdated();
            receiveIslandsUpdated();
        }

        //after moving motherNature;
        receiveMotherNaturePos();
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("A player moved MotherNature");});



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
        receiveSchoolsUpdated();
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("A player choose a cloud");});
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
    public static void receiveSchoolsUpdated() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
        for(int i=0; i< Eriantys.getCurrentApplication().getGameMode().getTeamsNumber()*Eriantys.getCurrentApplication().getGameMode().getTeamPlayers();i++) {
            SchoolData schoolData = (SchoolData) ois.readObject();
            //updating game data
            Eriantys.getCurrentApplication().getGameData().putSchoolData(schoolData.getMagician(), schoolData);
        }
        /*System.out.println("School of the "+schoolData.getMagician()+" updated: ");
        System.out.println("Money: "+schoolData.getPlayerMoney());
        System.out.print("Entrance: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getEntranceStudentsNum(color)+" | ");
        System.out.println();
        System.out.print("Hall: ");
        for(Color color: Color.values()) System.out.print("Color "+color+": "+schoolData.getHallStudentsNum(color)+" "+schoolData.hasProfessor(color)+" | ");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");*/
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
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("A player choose to use character"+charId);});
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
        return MessagesMethods.standardWait();
    }

    /**
     * handles the waiting while the MovingClient is using the Character3
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private static boolean characterWait3() throws IOException, ClassNotFoundException {
        receiveIslandsUpdated();
        boolean gameEnd = Eriantys.getCurrentApplication().getOis().readBoolean();
        if(gameEnd){
            return gameEnd;
        }
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait4() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait6() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait8() throws IOException, ClassNotFoundException {
        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait9() throws IOException, ClassNotFoundException {
        Color colorChosen = (Color) Eriantys.getCurrentApplication().getOis().readObject();
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("The player choose: "+colorChosen);});

        //receiving the MovingClient updated money
        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        return MessagesMethods.standardWait();
    }

    private static boolean characterWait10() throws IOException, ClassNotFoundException {
        Platform.runLater(()->{Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("The player has swapped some students in his school");});
        receiveSchoolsUpdated();
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

        receiveSchoolsUpdated();
        //receiving the characters updated prices
        receiveUpdatedPrices();
        //continuing with the standard wait
        return MessagesMethods.standardWait();
    }


}
