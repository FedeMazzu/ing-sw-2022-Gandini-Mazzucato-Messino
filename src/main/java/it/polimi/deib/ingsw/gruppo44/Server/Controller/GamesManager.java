package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.GameMode;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * class to manage the games
 * Note that it manages the Game controllers, but each controlled is associate
 * to exactly one game
 * @author filippogandini
 */
public class GamesManager {
    private Map<String,GameController> games;
    Scanner sc = new Scanner(System.in);

    public GamesManager(){
        this.games = new HashMap<>();
    }

    /**
     * Creates and runs the game
     */
    public void createGame(){
        String gameName = askGameName();
        GameMode gameMode = askGameMode();
        GameController gameController = new GameController(gameName,gameMode);
        games.put(gameName,gameController);

        new Thread(gameController).start();
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
                return GameMode.TwoPlayersBasic;
            case 2:
                return GameMode.TwoPlayersExpert;
            case 3:
                return GameMode.ThreePlayersBasic;
            case 4:
                return GameMode.ThreePlayersExpert;
            case 5:
                return GameMode.TeamBasic;
            default://else
                return GameMode.TeamExpert;
        }
    }

    public static void main(String[] args) {
        GamesManager gamesManager = new GamesManager();
        gamesManager.createGame();
    }
}
