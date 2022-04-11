package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game stages
 * @author filippogandini
 */
public class GameController {
    private Game game;
    private List<User> users;
    private Stage stage;
    private GameStage gameStage;
    private boolean endGame;

    public GameController() {
        users = new ArrayList<>();
    }

    /**
     * method to manage the stages. The stages handle the current game procedures
     * and, at the end, update the attribute gameStage to the next.
     */
    public void game(){
        endGame = false;
        gameStage = GameStage.START;
        while(!endGame){
            switch (gameStage){
                case START: stage = new Start(this);
                case PLANNING: stage = new Planning(this);
                case ACTION: stage = new Action(this);
                case END: stage = new End(this);
            }
            stage.handle();
        }
    }

    /**
     * method called from the START stage
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * method called from the START stage
     * @param user
     */
    public void addUser(User user){
        users.add(user);
    }

    /**
     * method called from the END stage to quit che loop and end the game
     */
    void EndGame(){endGame = true;}

    /**
     * method called from every stage at the end to move to the next stage
     * @param gameStage
     */
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }
}
