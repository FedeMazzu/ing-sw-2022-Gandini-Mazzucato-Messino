package it.polimi.deib.ingsw.gruppo44.Server.Model;
import it.polimi.deib.ingsw.gruppo44.Observable;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the schools
 * @author filippogandini
 */
public class School implements Observable {
    private final Player player;
    private List<School> schools;
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int schoolTowersNumber;//note that it's just for the GUI, the towers are owned by the team, not the school. the Class lacks of the methods to manage this value;
    private SchoolData schoolData;
    private final int maxEntranceStudentsNum;
    /**
     * Constructor. It initializes the maps keeping track of the students and professors
     * @param player associated to the school
     */
    public School(Player player,int entranceStudentsNum){
        this.player = player;
        schools = new ArrayList<>();
        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        professors = new HashMap<>();
        schoolData = new SchoolData(this);
        this.maxEntranceStudentsNum =entranceStudentsNum;
        //it's necessary because the colors(keys) aren't assigned by default
        for(Color color : Color.values()){
            hallStudents.put(color, 0);
            entranceStudents.put(color, 0);
            professors.put(color, false);
        }
    }

    /**
     * method to add a student in the hall that checks(and provides) if the professor is earned
     * and if the player gets a coin
     * @param color of the student to add
     */
    public void addHallStudent(Color color){
        hallStudents.put(color, hallStudents.get(color)+1);
        earnProfessor(color);//earns the professor if deserved
        if(hallStudents.get(color) % 3 ==0) player.addCoin();
        notifyObserver();
    }

    public void addEntranceStudent(Color color){
        int actualEntranceStudentsNum = 0;
        try {
            for(Color col: Color.values()) actualEntranceStudentsNum += getEntranceStudentsNum(col);
            if(actualEntranceStudentsNum >= maxEntranceStudentsNum) throw new Exception();
            entranceStudents.put(color, entranceStudents.get(color) + 1);
            notifyObserver();
        } catch(Exception e){
            //handle it ç
        }
    }
    public void addSchool(School school){
        schools.add(school);
    }

    /**
     * method to earn the professor if deserved
     * @param color of the professor it's being checked
     */
    private void earnProfessor(Color color) {
        boolean earnProfessor = true;
        int numStudents = hallStudents.get(color);
        int numStudentsOtherSchool;
        for(School s : schools) {
            if(s.hasProfessor(color)) {
                if (s.hallStudents.get(color) >= numStudents) {
                    earnProfessor = false;
                }else{
                    s.professors.put(color,false);
                }
                break; //because the professor can be owned at most by one school
            }
        }
        if(earnProfessor){
            professors.put(color, true);
        }
        notifyObserver();;
    }
    //METHOD ONLY FOR TESTING ç
    public void TESTsetProfessor(Color color){
        professors.put(color,true);
    }

    public void TESTnoProfessor(Color color){
        professors.put(color,false);
    }

    /**
     * method to notify the observer after a change
     */
    @Override
    public void notifyObserver(){ schoolData.update();}

    /**
     * @param color of the professor to check
     * @return true if the school has the professor of the passed color (false otherwise)
     */
    public boolean hasProfessor(Color color){
        return professors.get(color);
    }

    /**
     * @param color
     * @return the number of hallStudents of color
     */
    public int getHallStudentsNum(Color color){
        return hallStudents.get(color);
    }

    /**
     * @param color
     * @return the number of entranceStudents of color
     */
    public int getEntranceStudentsNum(Color color){
        return entranceStudents.get(color);
    }

    public int getMaxEntranceStudentsNum() {
        return maxEntranceStudentsNum;
    }

    public Player getPlayer() {
        return player;
    }
}