package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game stages
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
    private boolean gameIsFull;
    private TurnHandler turnHandler;
    private boolean loadedGame;



    public GameController(String gameName, GameMode gameMode) {
        this.gameName = gameName;
        this.gameMode = gameMode;
        this.gameStage = GameStage.START;
        users = new ArrayList<>();
        this.loadedGame = false;
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
            try {
                stage.handle();
            }catch (IOException | InterruptedException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if there are the conditions for ending the game
     * @return a boolean value
     */
    public boolean checkEndOfGame(){
        boolean COND1 = false, COND2 = false, COND3 = false, COND4 = false;
        //team tower supply is empty COND1
        for(Team team:game.getTeams()){
            if(team.getTowerCount() <= 0) COND1 = true;
        }
        //3 or less islands COND2
        if(game.getBoard().getUnionFind().getSize() <= 3) COND2 = true;
        //at the end of the round when there are no students in the bag OR a player has player their last card COND3
        for(Team team:game.getTeams()){
            for(Player p:team.getPlayers())
                if(p.showAvailableCards().isEmpty()) COND3 = true;
        }
        if(game.getBoard().getNotOwnedObjects().getStudentsSize() <= 0) COND3 = true;

        //checking if there are enough students to play another turn
        if(users.size()==2){
            if(game.getBoard().getNotOwnedObjects().getStudentsSize()<6) {
                COND4 = true;
            }
        }else{ // case 3 or 4 players
               if(game.getBoard().getNotOwnedObjects().getStudentsSize()<12){
                   COND4 = true;
               }
        }

        return COND1 || COND2 || COND3 || COND4;

    }


    /**
     * loads the serialized gameController (which includes the game)
     * @param parameterGameName
     * @return the game deserialized
     */
    public static GameController loadGame(String parameterGameName){
        try {
            FileInputStream fileIn = new FileInputStream("savedGames/"+parameterGameName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            GameController gameController = (GameController) in.readObject();
            in.close();
            fileIn.close();
            return gameController;
        }catch (IOException | ClassNotFoundException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     * saves the current game on a file named <GameName>.ser
     */
    public void saveGame(String gameName){
        try {
            //creates the directory if it doesn't exist yet
            new File("savedGames").mkdir();
            FileOutputStream fileOut = new FileOutputStream("savedGames/"+gameName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
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

    public Data getData() {
        return data;
    }

    /**
     * method called from the GamesManager
     * @param user
     */
    public synchronized boolean addUser(User user){
        if(users.size() >= gameMode.getTeamPlayers()*gameMode.getTeamsNumber()){
            return false;
        }
        users.add(user);
        notifyAll(); // to wake up the thread executing the while in the start waiting for the correct number of users
        return true;
    }

    /**
     * @return the number of users participating the game
     */
    public  synchronized int getNumUsers(){
        return users.size();
    }


    /**
     * method called from the END stage to quit che loop and end the game
     */
    public void endGame(){endGame = true;}

    /**
     * method called from every stage at the end to move to the next stage
     * @param gameStage
     */
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }


    public void setTurnHandler(TurnHandler turnHandler){ this.turnHandler = turnHandler; }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getGameName() {
        return gameName;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public User getUser(int index){
        return users.get(index);
    }

    public void setGameIsFull(boolean gameIsFull) {
        this.gameIsFull = gameIsFull;
    }

    public boolean getGameIsFull() {
        return gameIsFull;
    }

    public boolean isLoadedGame() {
        return loadedGame;
    }

    public void setLoadedGame(boolean loadedGame) {
        this.loadedGame = loadedGame;
    }

    public void clearUsers() {
        users.clear();
    }
}
