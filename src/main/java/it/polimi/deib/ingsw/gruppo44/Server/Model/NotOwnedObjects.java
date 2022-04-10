package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.util.*;

/**
 * class to keep track of the unused objects
 * @author zenomess
 */

public class NotOwnedObjects{
    //private Map<Color, Integer> students;
    private Map<Color, Boolean> freeProfessors;
    private int bankMoney;
    private GameMode gameMode;
    private List<Color> students;

    public NotOwnedObjects(GameMode gameMode){
        this.gameMode = gameMode;
        //students = new HashMap<>();
        //for(Color color : Color.values())
        //students.put(color, 24);
        freeProfessors = new HashMap<>();
        for(Color color1 : Color.values())
            freeProfessors.put(color1, true);
        if(gameMode.isExpertMode()) bankMoney=20;
        else bankMoney=0;
        students = new ArrayList<>();
        for(Color c : Color.values()){
            for(int i=0; i<24; i++)
                students.add(c);
        }
    }

    public void giveCoin(){
        bankMoney--;
    }

    public void fillCloud(Cloud cloud){
        Random rand = new Random();
        int randIndex;
        for(int i=0; i<gameMode.getCloudsNumbers(); i++){
            randIndex = rand.nextInt(students.size());
            Color tempColor = students.get(randIndex);
            cloud.addStudent(tempColor);
            students.remove(randIndex);
        }

    }

    //public void fillEntrances(){}



}