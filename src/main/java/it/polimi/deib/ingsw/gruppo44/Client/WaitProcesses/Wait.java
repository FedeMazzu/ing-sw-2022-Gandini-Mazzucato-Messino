package it.polimi.deib.ingsw.gruppo44.Client.WaitProcesses;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers.IslandsSceneController;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class Wait implements Runnable{

    private int turnNumber;
    private boolean gameEnd;


    @Override
    public void run() {
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();
        try{
            turnNumber = ois.readInt();

            int counter = turnNumber;
            System.out.println("turn Number: "+turnNumber);
            //receiving the data when the player isn't moving yet
            while(counter>0){
                //receive the outputs of the turnNumber players before you
                boolean usingCharacter = false;
                if(Eriantys.getCurrentApplication().getGameMode().isExpertMode()) usingCharacter = ois.readBoolean();
                if(usingCharacter){
                    gameEnd = MessagesMethods.characterWait();
                    if(gameEnd) return; //Switch to gameEnd scene
                }
                else{
                    gameEnd = MessagesMethods.standardWait();
                    if(gameEnd) return; //Switch to gameEnd scene
                }
                counter--;
            }
        }
        catch (Exception e){}
        //it's your turn to move
        //we show entrance students table

        //remember to ask if the player wants to use any character and go in expert mode
        playStandardTurn();
        while(Eriantys.getCurrentApplication().getIslandsSceneController().getPhase() != -1){
            try {
                synchronized (Eriantys.getCurrentApplication().getIslandsSceneController()){
                    Eriantys.getCurrentApplication().getIslandsSceneController().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            waitAfter();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void waitAfter() throws IOException, ClassNotFoundException {
        System.out.println("Siamo entrati in wait after");
        int counter = turnNumber+1;
        int numOfPlayers = Eriantys.getCurrentApplication().getGameMode().getTeamPlayers() * Eriantys.getCurrentApplication().getGameMode().getTeamsNumber();
        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();

        while (numOfPlayers - counter >0){
            //receive the outputs of the turnNumber players after you
            boolean usingCharacter = false;
            if(Eriantys.getCurrentApplication().getGameMode().isExpertMode()) usingCharacter = ois.readBoolean();
            if(usingCharacter){
                gameEnd = MessagesMethods.characterWait();
                if(gameEnd) return; //Switch to gameEnd scene
            }
            else{
                gameEnd = MessagesMethods.standardWait();
                if(gameEnd) return; //Switch to gameEnd scene
            }
            counter++;
        }
        new Thread(new WaitCards()).start();

    }

    public void playStandardTurn(){
        //setting the phase attribute to "wake up" events
        Eriantys.getCurrentApplication().getIslandsSceneController().setPhase(0);
        //put the student choice panel visible
        Platform.runLater(()->{
            Rectangle rectangle = Eriantys.getCurrentApplication().getIslandsSceneController().getStudentChoicePanel();
            ListView <Color> entranceStudentsSel = Eriantys.getCurrentApplication().getIslandsSceneController().getEntranceStudentsSelection();
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


        });
    }

}
