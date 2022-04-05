package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.List;
import java.util.Map;

/**
 * class to represent the board of the game
 * @author filippogandini
 */

public class Board implements Data {
    private int motherNaturePosition;
    private Map<Color, Player> professors;
    private List<Cloud> clouds;
    private Shop shop;
    private UnionFind unionFind;
    private NotOwnedObjects notOwnedObjects;

    /**
     *  method to save the board's data
     */
    @Override
    public void save(){}

}
