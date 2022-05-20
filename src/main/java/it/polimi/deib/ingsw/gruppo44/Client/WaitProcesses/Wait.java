package it.polimi.deib.ingsw.gruppo44.Client.WaitProcesses;

import it.polimi.deib.ingsw.gruppo44.Client.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try {
            while(Eriantys.getCurrentApplication().getIslandsSceneController().getPhase() != -1) {
                synchronized (Eriantys.getCurrentApplication().getIslandsSceneController()) {
                    Eriantys.getCurrentApplication().getIslandsSceneController().wait();
                }
            }
                waitAfter();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
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
        Eriantys.getCurrentApplication().getIslandsSceneController().setPhase(-2);
        //put the student choice panel visible
        Platform.runLater(()->{
            if(Eriantys.getCurrentApplication().getGameMode().isExpertMode()){
                Map<Integer,Integer> affChars = Eriantys.getCurrentApplication().getGameData().getAffordableCharacters();
                ObjectOutputStream oos =Eriantys.getCurrentApplication().getOos();
                for(int val:affChars.keySet()) {
                    Eriantys.getCurrentApplication().getShopSceneController().cglFromId(val).getHighlight().setVisible(false);
                }
                if(!affChars.isEmpty()){
                    for(int val:affChars.keySet()){
                        Eriantys.getCurrentApplication().getShopSceneController().cglFromId(val).getHighlight().setVisible(true);
                    }
                    Eriantys.getCurrentApplication().getShopSceneController().getNotBuyButton().setVisible(true);
                    Eriantys.getCurrentApplication().switchToShopScene();
                }
                else{
                    try {
                        oos.writeBoolean(false);
                        oos.flush();
                    } catch (IOException e) {e.printStackTrace();}
                    MessagesMethods.setForMovingStudents();
                }
            }
            else{
                MessagesMethods.setForMovingStudents();
            }

        });
    }


}
