package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * class to keep track of the unused objects
 * @author filippogandini
 */

public class NotOwnedObjects{
    //private Map<Color, Integer> students;
    private Map<Color, Boolean> freeProfessors;
    private int bankMoney;
    private GameMode gameMode;
    private Random rand;
    private ArrayList<Color> Arr;

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
        Arr = new ArrayList<>();
        rand = new Random();
        for(Color c : Color.values()){
            for(int i=0; i<24; i++)
                Arr.add(c);
        }
    }

    public void fillClouds(Cloud[] clouds){
        for(int i=0; i<gameMode.getCloudsNumbers(); i++){
            for(int p=0; p<gameMode.getCloudStudents(); p++) {
                int randIndex = rand.nextInt(Arr.size());
                Color tempColor = Arr.get(randIndex);
                clouds[i].addStudent(tempColor);
                Arr.remove(randIndex);
            }
        }
    }

    public void fillEntrances()



}
