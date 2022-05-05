package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * class to represent the islands
 * @author FedeMazzu
 */
public class Island implements Observable, Serializable {
    private UnionFind unionFind;
    private Map<Color, Integer> students;
    private Tower tower;
    private int islandID;
    private boolean hasTower;
    private IslandsObserver islandsObserver;

    public Island(UnionFind uf,int  id){
        islandID = id;
        students = new HashMap<>();
        for(Color color:Color.values()) {
            students.put(color, 0);
        }
        unionFind = uf;
        hasTower = false;

    }

    public void notifyObserver(){
        islandsObserver.update(islandID);
    }

    /**
     * @param team
     * @return the influence score of the passed team
     */
    private int influence(Team team){
        int score = 0;
        School school;

        if(unionFind.getCharacterUsed() != 6){
            //character 6 ignores towers while counting influence in islands
            if(!hasTower) score = 0;
            else score = unionFind.getGroupSize(this.islandID);
        }

        for(Player player : team.getPlayers()){
            school = player.getSchool();
            for(Color color : Color.values()){
                if(unionFind.getCharacterUsed() == 9 && color.equals(unionFind.getColorChosenForChar9())) continue;
                if(school.hasProfessor(color)) score += students.get(color);
            }
        }

        return score;
    }

    /**
     * Does all the computation needed when mother nature ends on this island
     */
    public void islandClaim(){
        int bestScore = 0;
        int secondBestScore = 0; //this is used to handle the equal influence case
        Team bestTeam = null;

        for(Team t : unionFind.teams){
            int tempScore = influence(t);
            //when using character 8 the team counts 2 more points in its influence score
            if(unionFind.getCharacterUsed() == 8 && t.equals(unionFind.teams.get(unionFind.getCurrTeamIndexForChar8()))){
                tempScore+=2;
            }
            if(tempScore >= bestScore){
                secondBestScore = bestScore;
                bestScore = tempScore;
                bestTeam = t;
            }
        }

        if(bestScore == secondBestScore) bestTeam = null; //this is used to handle the equal influence case

        try{
            if(bestTeam == null) throw new Exception();
            if(getHasTower() && getTowerColor().equals(bestTeam.getTower())) throw new Exception();

            //From here on if there are towers they are of a color that is not of the bestTeam

            if(getHasTower()){
                //there is at least one tower
                int islandSize = unionFind.getGroupSize(islandID);
                for(Team t : unionFind.teams){
                    if(t.getTower() == getTowerColor()) t.setTowerCount(t.getTowerCount() + islandSize);
                }
                tower = bestTeam.getTower();
                bestTeam.setTowerCount(bestTeam.getTowerCount() - islandSize); // negative count is ok as long as the win condition checks for it
            }
            else{
                //there aren't any towers
                tower = bestTeam.getTower();
                bestTeam.setTowerCount(bestTeam.getTowerCount() - 1);
                hasTower = true;
            }

            //check for merge conditions

            int currGroup = unionFind.findGroup(islandID);
            int pos = currGroup;

            //forward
            while(currGroup == unionFind.findGroup(pos)){
                pos = (pos+1)%12;
            }
            Island forwardGroup = unionFind.getIsland(pos);
            if(forwardGroup.hasTower && unionFind.getIsland(pos).getTowerColor().equals(tower)){
                unionFind.merge(this,forwardGroup);
            }

            //check win conditions ç

            //backward
            pos = currGroup;
            while(currGroup == unionFind.findGroup(pos)){
                pos = (pos-1)%12;
            }
            Island backwardGroup = unionFind.getIsland(pos);
            if(backwardGroup.hasTower && unionFind.getIsland(pos).getTowerColor().equals(tower)){
                unionFind.merge(this,backwardGroup);
            }

            //check win conditions ç
            notifyObserver();
        }
        catch (Exception e){
            //In case there isn't a best team or if the color of the tower is the same of the best team
            return;
        }


    }

    public void addStudent(Color color) {
        students.put(color,students.get(color)+1);
        notifyObserver();
    }


    public void setIslandsObserver(IslandsObserver islandsObserver) {
        this.islandsObserver = islandsObserver;
    }


    public int getIslandID() {
        return islandID;
    }

    public int getStudentNum(Color color){
        return students.get(color);
    }

    public void setStudents(Color color, int val){
        students.put(color,val);
    }

    public boolean getHasTower() {
        return hasTower;
    }

    public Tower getTowerColor(){ return tower;};

    public UnionFind getUnionFind() {
        return unionFind;
    }
}