package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the game
 */
public class Game implements Serializable {
    private List<Team> teams;
    private Board board;

    /**
     * Constructor which initializes the game
     * @param gameMode
     */
    public Game(GameMode gameMode){
        teams = new ArrayList<>();
        // the index i is necessary to count the initialized teams
        int i=0;
        board = new Board(gameMode,this);
        for(Tower tower : Tower.values()){
            if(i == gameMode.getTeamsNumber()) break;
            teams.add(new Team(tower, gameMode,this));
            i++;
        }

    }


    public Board getBoard(){
        return board;
    }

    public List <Team> getTeams(){
        return teams;
    }


}
