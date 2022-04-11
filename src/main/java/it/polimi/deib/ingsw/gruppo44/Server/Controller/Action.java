package it.polimi.deib.ingsw.gruppo44.Server.Controller;

public class Action implements Stage {
    private final GameStage gameStage = GameStage.ACTION;
    private final GameController gameController;

    public Action(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() {

    }
}
