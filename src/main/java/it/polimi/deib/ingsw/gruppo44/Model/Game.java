package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the game
 * @author filippogandini
 */
public class Game {
    static public List<Team> teams;
    private Board board;

    /**
     * Constructor which initializes the game
     * @param gameMode
     */
    public Game(GameMode gameMode){
        teams = new ArrayList<>();
        // the index i is necessary to count the initialized teams
        int i=0;
        board = new Board(gameMode);
        for(Tower tower : Tower.values()){
            if(i == gameMode.getTeamsNumber()) break;
            teams.add(new Team(tower, gameMode));
            i++;
        }

    }

    public Board getBoard(){
        return board;
    }


}
