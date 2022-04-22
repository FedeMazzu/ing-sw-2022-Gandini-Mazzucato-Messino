package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Board;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Cloud;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;

public class Cleanup implements Stage {
    private final GameStage gameStage = GameStage.CLEANUP;
    private final GameController gameController;

    public Cleanup(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() {
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
        //check end of game conditions
        gameController.setGameStage(GameStage.PLANNING);
    }
}
