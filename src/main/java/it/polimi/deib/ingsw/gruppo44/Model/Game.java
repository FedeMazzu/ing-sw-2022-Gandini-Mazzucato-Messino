package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the game
 * @author filippogandini
 */
public class Game {
    private List<Data> data;

    /**
     * Constructor which initializes the game
     * @param gameMode
     */
    public Game(GameMode gameMode){
        data = new ArrayList<>();
        // the index i is necessary to count the initialized teams
        int i=0;
        for(Tower tower : Tower.values()){
            if(i == gameMode.getTeamsNumber()) break;
            data.add(new Team(tower, gameMode));
            i++;
        }
        new Board(gameMode);
    }


}
