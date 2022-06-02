package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.MessagesMethodsGUI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class to manage the client who has already moved and is waiting the end of the other players turn
 * and, in the meanwhile, is receiving the updates of the moves made by the player who's moving
 */
public class WaitingAfterTurn implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private int turnNumber;
    private boolean gameEnd;

    public WaitingAfterTurn(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        turnNumber = clientController.getTurnNumber();
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {
        int counter = turnNumber + 1;
        int numberOfPlayers = clientController.getGameMode().getTeamPlayers() * clientController.getGameMode().getTeamsNumber();
        while(numberOfPlayers - counter>0){
            System.out.println("Waiting, another player is moving...");
            //get if another player is using a character
            boolean usingCharacter = false;
            if(clientController.getGameMode().isExpertMode()) usingCharacter = ois.readBoolean();
            if(usingCharacter){
                gameEnd = MessagesMethodsCLI.characterWait();
                if(gameEnd) return;
            }
            else{
                gameEnd = MessagesMethodsCLI.standardWait();
                if(gameEnd) return;
            }

            counter++;
        }

        clientController.setClientStage(ClientStage.ClientPLANNING);
    }



}
