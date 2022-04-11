package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

public class Planning implements Stage {
    private final GameStage gameStage = GameStage.PLANNING;
    private final GameController gameController;

    public Planning(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() {


    }
}
