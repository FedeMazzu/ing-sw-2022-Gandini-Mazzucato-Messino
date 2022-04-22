package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game stages
 * @author filippogandini
 */
public class GameController {
    private Game game;
    private Data data;
    private List<User> users;
    private Stage stage;
    private GameStage gameStage;
    private boolean endGame;
    private TurnHandler turnHandler;
    private GameMode gameMode;


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
                case START:
                    stage = new Start(this);
                    break;
                case PLANNING:
                    stage = new Planning(this);
                    break;
                case ACTION:
                    stage = new Action(this);
                    break;
                case CLEANUP:
                    stage = new Cleanup(this);
                    break;
                default: //case END
                    stage = new End(this);

            }
            stage.handle();
        }
    }

    public boolean checkEndOfGame(){
        boolean COND1 = false, COND2 = false, COND3 = false;
        //team tower supply is empty COND1
        for(Team team:game.getTeams()){
            if(team.getTowerCount() == 0) COND1 = true;
        }
        //3 or less islands COND2
        if(game.getBoard().getUnionFind().getSize() <= 3) COND2 = true;
        //at the end of the round when there are no students in the bag OR a player has player their last card COND3
        for(Team team:game.getTeams()){
            for(Player p:team.getPlayers())
                if(p.showAvailableCards().isEmpty()) COND3 = true;
        }
        if(game.getBoard().getNotOwnedObjects().getStudentsSize() <= 0) COND3 = true;

        return COND1 || COND2 || COND3;

    }


    /**
     * method called from the START stage
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() { return game; }

    /**
     * method called from the START stage
     * @param data
     */
    public void setData(Data data) {
        this.data = data;
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

    public void setTurnHandler(TurnHandler turnHandler){ this.turnHandler = turnHandler; }

    public void setGameMode(GameMode gameMode) { this.gameMode = gameMode; }

    public GameMode getGameMode() {
        return gameMode;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.game();
    }
}
