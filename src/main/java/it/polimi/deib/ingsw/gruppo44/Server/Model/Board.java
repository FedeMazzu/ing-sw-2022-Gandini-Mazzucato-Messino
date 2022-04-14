package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the board of the game
 * @author FedeMazzu
 */

public class Board implements SaveData {
    private int motherNaturePosition;
    private Map<Color, Player> professors;
    private List<Cloud> clouds;
    private Shop shop;
    private UnionFind unionFind;
    private NotOwnedObjects notOwnedObjects;
    private int numOfPlayers;
    private CloudsObserver cloudsObserver;

    public Board(GameMode gameMode, Game game) {
        motherNaturePosition = 0;
        professors = new HashMap<Color,Player>();
        clouds = new ArrayList<Cloud>();
        unionFind = new UnionFind(game);
        notOwnedObjects = new NotOwnedObjects(gameMode);
        numOfPlayers = gameMode.getTeamPlayers() * gameMode.getTeamsNumber();
        // remember multithread implementation
        if(gameMode.isExpertMode()) shop = new Shop();
        cloudsObserver = new CloudsObserver(gameMode.getCloudsNumber());
        Cloud cloud;
        for(int p = 0; p< gameMode.getCloudsNumber(); p++){
            cloud = new Cloud(gameMode.getCloudStudents(),p);
            clouds.add(cloud);
            cloud.setCloudsObserver(cloudsObserver);
            cloudsObserver.addCloud(cloud);
            notOwnedObjects.fillCloud(cloud);
        }

    }

    /**
     * method to change mother nature's position on the islands
     * @param spaces number of spaces motherNature will move
     */
    //SHOULD BE PRIVATE? ç
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
        unionFind.getIsland(motherNaturePosition).islandClaim();
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }



    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public UnionFind getUnionFind() {
        return unionFind;
    }

    public NotOwnedObjects getNotOwnedObjects() {
        return notOwnedObjects;
    }

    public CloudsObserver getCloudsObserver() {
        return cloudsObserver;
    }

    /**
     * useful for testing
     * @return
     */

    public List<Cloud> getClouds() {
        return clouds;
    }

    /**
     *  method to save the board's data
     */
    @Override
    public void save(){}

}

