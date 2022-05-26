package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * class to manage the games
 * Note that it manages the Game controllers, but each controlled is associate
 * to exactly one game
 * @author filippogandini
 */
public class GamesManager implements Serializable {
    private Map<String,GameController> games;
    Scanner sc = new Scanner(System.in);


    public GamesManager(){
        this.games = new HashMap<>();
    }

    /**
     * Creates and runs the game
     */
    public void createGame(String gameName, GameMode gameMode, User creatorUser){
        GameController gameController = new GameController(gameName,gameMode);
        gameController.addUser(creatorUser);
        games.put(gameName,gameController);
        new Thread(gameController).start();
    }


    public boolean joinGame(String gameName,User joiningUser){
        GameController gameController = games.get(gameName);
        return gameController.addUser(joiningUser);
        //note that this just has to add the user to the appropriate GameController
    }


    /**
     * loads a game from a file .ser and runs it
      * @param gameName
     */
    public void loadGame(String gameName){
        //reinitialize because if a game can be loaded it has already been created and added to games
        GameController gameController = GameController.loadGame(gameName);
        games.put(gameController.getGameName(),gameController);

        new Thread(gameController).start();
    }




    private String askGameName() {
        System.out.println("Insert the game name:");
        return sc.next();
    }

    /**
     * @return the name and gameMode of the running games
     */
    public Map<String,GameMode> getOpenGames(){
        Map<String,GameMode> openGames = new HashMap<>();
        for(String s : games.keySet()){
            if(games.get(s).getGameIsFull()) continue;
            openGames.put(s, games.get(s).getGameMode());
        }
        return openGames;
    }


    /**
     * Temporary method used for trying
     * @return gameMode
     */
    private GameMode askGameMode()throws InputMismatchException {
        int a;
        do {
            System.out.println("Choose the game mode(number):\n" +
                    "1 - TwoPlayersBasic\n" +
                    "2 - TwoPlayersExpert\n" +
                    "3 - ThreePlayersBasic\n" +
                    "4 - ThreePlayersExpert\n" +
                    "5 - TeamBasic\n" +
                    "6 - TeamExpert");

            a = sc.nextInt();
        } while (a < 1 || a > 6);
        switch (a) {
            case 1:
                return GameMode.TwoPlayersStandard;
            case 2:
                return GameMode.TwoPlayersExpert;
            case 3:
                return GameMode.ThreePlayersStandard;
            case 4:
                return GameMode.ThreePlayersExpert;
            case 5:
                return GameMode.TeamStandard;
            default://else
                return GameMode.TeamExpert;
        }
    }

}
