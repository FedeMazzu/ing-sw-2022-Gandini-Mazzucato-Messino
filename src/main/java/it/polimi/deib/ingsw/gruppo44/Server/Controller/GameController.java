package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game stages
 * @author filippogandini
 */
public class GameController implements Serializable, Runnable {
    private Game game; //identifier
    private final String gameName;
    private final GameMode gameMode;
    private Data data;
    private List<User> users;
    private Stage stage;
    private GameStage gameStage;
    private boolean endGame;
    private TurnHandler turnHandler;



    public GameController(String gameName, GameMode gameMode) {
        this.gameName = gameName;
        this.gameMode = gameMode;
        this.gameStage = GameStage.START;
        users = new ArrayList<>();
    }

    /**
     * method to manage the stages. The stages handle the current game procedures
     * and, at the end, update the attribute gameStage to the next.
     */
    @Override
    public void run(){
        endGame = false;
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

    /**
     * checks if there are the conditions for ending the game
     * @return a boolean value
     */
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
     * saves the current game on a file named <GameName>.ser
     */
    public void saveGame(){
        try {
            FileOutputStream fileOut = new FileOutputStream(gameName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            //needed?
            // out.flush();
            out.close();
            fileOut.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * loads the serialized gameController (which includes the game)
     * @param parameterGameName
     * @return the game deserialized
     */
    public static GameController loadGame(String parameterGameName){
        try {
            FileInputStream fileIn = new FileInputStream(parameterGameName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            GameController gameController = (GameController) in.readObject();

            //needed?
            // in.flush();
            in.close();
            fileIn.close();
            return gameController;
        }catch (IOException | ClassNotFoundException ioe){
            ioe.printStackTrace();
            return null;
        }
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
    void endGame(){endGame = true;}

    /**
     * method called from every stage at the end to move to the next stage
     * @param gameStage
     */
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }


    public void setTurnHandler(TurnHandler turnHandler){ this.turnHandler = turnHandler; }


    public GameMode getGameMode() {
        return gameMode;
    }

    public String getGameName() {
        return gameName;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }
}
