package it.polimi.deib.ingsw.gruppo44.Server.Controller;

public class End implements Stage {
    private final GameStage gameStage = GameStage.END;
    private final GameController gameController;

    public End(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle() {

    }
}
