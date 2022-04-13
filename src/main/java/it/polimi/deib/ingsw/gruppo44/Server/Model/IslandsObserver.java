package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;

/**
 * Observer of the islands;
 */
public class IslandsObserver{
    private IslandsData islandsData;
    private Island[] islands;

    public IslandsObserver(Island[] islands){
        this.islands = islands;
        islandsData = new IslandsData();
    }


    /**
     * method used to update a single island
     * @param islandId
     */
    public void update(int islandId){
        boolean hasTower = islands[islandId].getHasTower();
        islandsData.setHasTower(islandId,hasTower);
        // to avoid null values for the tower type and unuseful computation
        if(hasTower){ //note that at the start hasTower is already initialized to false for all the islands by default values
            islandsData.setHasTower(islandId,hasTower);
            islandsData.setTower(islandId,islands[islandId].getTowerColor());
            //it can be called on every island
            UnionFind unionFind = islands[0].getUnionFind();
            //in general, it's necessary to reset the group value of all the islands
            for(int i=0; i<12;i++) {
                islandsData.setGroup(i, unionFind.getGroup(i));
                islandsData.setGroupSize(i, unionFind.getGroupSize(i));
            }
        }

        for(Color color: Color.values()){
            islandsData.setStudentsNum(islandId,color,islands[islandId].getStudentNum(color));
        }

    }

    public IslandsData getIslandsData() {
        return islandsData;
    }
}
