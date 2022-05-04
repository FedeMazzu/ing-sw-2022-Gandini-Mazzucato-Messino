package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * stage to manage the client who has already moved and is waiting the end of the other palyers turn
 * and, in the meanwhile, is receiving the updates of the moves made by the player who's moving
 * @author filippogandini
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
                gameEnd = MessagesMethods.characterWait();
                if(gameEnd) return;
            }
            else{
                gameEnd = MessagesMethods.standardWait();
                if(gameEnd) return;
            }

            counter++;
        }

        clientController.setClientStage(ClientStage.ClientPLANNING);
    }



}
