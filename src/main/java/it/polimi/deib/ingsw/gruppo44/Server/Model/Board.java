package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.Observable;
import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the board of the game
 */

public class Board implements Observable, Serializable {
    private int motherNaturePosition;
    private Map<Color, Player> professors;
    private List<Cloud> clouds;
    private Shop shop;
    private UnionFind unionFind;
    private NotOwnedObjects notOwnedObjects;
    private int numOfPlayers;
    private BoardObserver boardObserver;
    private CloudsObserver cloudsObserver;

    public Board(GameMode gameMode, Game game) {
        motherNaturePosition = 0;
        professors = new HashMap<Color,Player>();
        clouds = new ArrayList<Cloud>();
        unionFind = new UnionFind(game);
        notOwnedObjects = new NotOwnedObjects(gameMode);
        numOfPlayers = gameMode.getTeamPlayers() * gameMode.getTeamsNumber();
        boardObserver = new BoardObserver(this,gameMode);
        cloudsObserver = new CloudsObserver(gameMode.getCloudsNumber());
        Cloud cloud;
        for(int p = 0; p< gameMode.getCloudsNumber(); p++){
            cloud = new Cloud(gameMode.getCloudStudents(),p);
            clouds.add(cloud);
            cloud.setCloudsObserver(cloudsObserver);
            cloudsObserver.addCloud(cloud);
            notOwnedObjects.fillCloud(cloud);
        }

        if(gameMode.isExpertMode()) shop = new Shop(game,boardObserver);

        boardObserver.update();

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
        unionFind.getIsland(motherNaturePosition).islandClaim();
        notifyObserver();
    }


    @Override
    public void notifyObserver() {
        boardObserver.update();
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

    public NotOwnedObjects getNotOwnedObjects() {return notOwnedObjects;}

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

    public BoardObserver getBoardObserver() {
        return boardObserver;
    }

    public Shop getShop() {return shop;}


}

