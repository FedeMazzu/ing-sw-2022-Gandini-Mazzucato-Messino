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
        // the index i is necessary for a correct initialization of the Tower type
        for(int i=0; i< gameMode.getTeamsNumber();i++) data.add(new Team(Tower.getColorById(i),gameMode));
        new Board(gameMode);
    }


}
