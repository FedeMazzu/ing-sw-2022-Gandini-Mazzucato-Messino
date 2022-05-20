package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * stage to manage the client waiting to play his turn and receiving the updates of the moves made
 * by the player who's moving
 * @author filippogandini
 */
public class WaitingBeforeTurn implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private int turnNumber;
    private boolean gameEnd;

    public WaitingBeforeTurn(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        turnNumber = clientController.getTurnNumber();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Waiting for your turn to move..");
        int counter = turnNumber;
        //receiving the data when the player isn't moving yet
        while(counter>0){
            //receive the outputs of the turnNumber players before you
            boolean usingCharacter = false;
            if(clientController.getGameMode().isExpertMode()) usingCharacter = ois.readBoolean();
            if(usingCharacter){
                gameEnd = MessagesMethods.characterWait();
                if(gameEnd) return;
            }
            else{
                gameEnd = MessagesMethods.standardWait();
                if(gameEnd) return;
            }
            counter--;
        }
        clientController.setClientStage(ClientStage.ClientACTION);
    }




}
