package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.HashMap;
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
    private int numOfPlayers;

    public Board(int playerNum) {
        motherNaturePosition = 0;
        professors = new HashMap<Color,Player>();
        clouds = new ArrayList<Cloud>();
        shop = new Shop();
        unionFind = new UnionFind();
        notOwnedObjects = new NotOwnedObjects();
        numOfPlayers = playerNum;

        for(int p = 0; p<playerNum;p++){
            Cloud tempCloud = new Cloud(playerNum);
            clouds.add(tempCloud);
        }


    }

    /**
     * method to change mother nature's position on the islands
     * @param spaces number of spaces motherNature will move
     */

    public void moveMotherNature(int spaces){
        int currIsland = getMotherNaturePosition();
        int currGroup = unionFind.findGroup(currIsland);
        while(spaces > 0){
            currIsland = (currIsland+1)%12;
            int tempGroup = unionFind.findGroup(currIsland);
            if(currGroup != tempGroup){
                currGroup = tempGroup;
                spaces--;
                motherNaturePosition = currGroup;
            }
        }
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }



    public int getNumOfPlayers() {
        return numOfPlayers;
    }




    /**
     *  method to save the board's data
     */
    @Override
    public void save(){}

}
