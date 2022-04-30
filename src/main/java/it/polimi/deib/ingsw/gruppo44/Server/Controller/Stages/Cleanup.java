package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Board;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Cloud;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * stage to clean up the game before starting a new round
 * @author
 */
public class Cleanup implements Stage, Serializable {
    private final GameStage gameStage = GameStage.CLEANUP;
    private final GameController gameController;
    private final GameMode gameMode;

    public Cleanup(GameController gameController) {

        this.gameController = gameController;
        this.gameMode = gameController.getGameMode();
    }

    @Override
    public void handle() throws IOException {
        System.out.println("--------------CLEANUP PHASE---------------");
        //fill clouds
        Board board= gameController.getGame().getBoard();
        for(Cloud cloud: board.getClouds()){
            board.getNotOwnedObjects().fillCloud(cloud);
        }
        for(int i=0;i< board.getClouds().size();i++){
            System.out.println("Cloud ID "+i);
            Cloud cloud = board.getClouds().get(i);
            for(Color c : Color.values()){
                System.out.println("Student: "+c+" "+cloud.getStudentsNum(c));
            }
        }


        gameController.setGameStage(GameStage.PLANNING);
    }
}
