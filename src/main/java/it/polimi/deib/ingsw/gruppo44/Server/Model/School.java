package it.polimi.deib.ingsw.gruppo44.Server.Model;
import it.polimi.deib.ingsw.gruppo44.Observable;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the schools
 * @author filippogandini
 */
public class School implements Observable, Serializable {
    private Player player;
    private List<School> schools;
    private Map<Color, Integer> hallStudents, entranceStudents;
    private Map<Color, Boolean> professors;
    private int schoolTowersNumber;//note that it's just for the GUI, the towers are owned by the team, not the school. the Class lacks of the methods to manage this value;
    private SchoolObserver schoolObserver;
    private final int maxEntranceStudentsNum;
    private final static int maxHallStudentsNum = 10;
    private boolean character2Used;
    /**
     * Constructor. It initializes the maps keeping track of the students and professors
     * @param player associated to the school
     */
    public School(Player player,int entranceStudentsNum){
        character2Used = false;
        this.player = player;
        schools = new ArrayList<>();
        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        professors = new HashMap<>();
        this.maxEntranceStudentsNum =entranceStudentsNum;
        //it's necessary because the colors(keys) aren't assigned by default
        for(Color color : Color.values()){
            hallStudents.put(color, 0);
            entranceStudents.put(color, 0);
            professors.put(color, false);
        }
        //it's necessary to instantiate it after the initialization of the maps
        schoolObserver = new SchoolObserver(this);
    }


    /**
     * method to add a student in the hall that checks(and provides) if the professor is earned
     * and if the player gets a coin
     * @param color of the student to add
     * @return boolean which indicates if the student was added correctly
     */
    public boolean addHallStudent(Color color) {
        try {
            int numStudents = hallStudents.get(color);
            if (!removeEntranceStudent(color)) throw new Exception();
            if (numStudents >= maxHallStudentsNum) throw new Exception();
            hallStudents.put(color, numStudents + 1);
            earnProfessor(color);//earns the professor if deserved
            if (hallStudents.get(color) % 3 == 0) player.addCoin();
            notifyObserver();
            return true;
        } catch (Exception e) {
            System.out.println("Hall already full for this color or entrance has no students of that color");
            return false;
        }
    }

    public void swapStudentsForChar10(Color colorHall, Color colorEntrance){
        removeHallStudent(colorHall);
        addHallStudent(colorEntrance);
        addEntranceStudent(colorHall);
    }

    /**
     * method called from the Character12 to play his effect
     * removes a student of the passed color (if there are)
     * @param color
     * @return weather it was removed or there weren't
     */
    public boolean removeHallStudent(Color color){
        try{
            int numStudents = hallStudents.get(color);
            if (numStudents<=0) throw new Exception();
            hallStudents.put(color, numStudents-1);
            //note that this lacks to update the count in the UnionFind
            notifyObserver();
            return true;
        }catch(Exception e){
            //do nothing... the effect of the character12 works like this

            //necessary to update the count in the Not Owned Objects
            return false;
        }
    }


    /**
     * @param color of the student to add
     * @return a boolean which indicates if the Entrance is already full;
     */
    public boolean addEntranceStudent(Color color){
        int actualEntranceStudentsNum = 0;
        try {
            for(Color col: Color.values()) actualEntranceStudentsNum += getEntranceStudentsNum(col);
            if(actualEntranceStudentsNum >= maxEntranceStudentsNum) throw new Exception();
            entranceStudents.put(color, entranceStudents.get(color) + 1);
            notifyObserver();
            return true;
        } catch(Exception e){
            System.out.println("Entrance already full");
            return false;
        }
    }

    /**
     * removes a studnet from the entrance if exists at least one of the Color passed
     * @param color
     * @return a boolean representing whether the student was removed or there weren't
     */
    public boolean removeEntranceStudent(Color color){
        if(entranceStudents.get(color) <= 0 ) return false;
        int previousNum = entranceStudents.get(color);
        entranceStudents.put(color,previousNum-1);
        notifyObserver();
        return true;
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

        System.out.println("STAMPA SCUOLE: \n"+schools);
        for(School s : schools) {
            if(s.hasProfessor(color)) {
                if (s.hallStudents.get(color) >= numStudents) {
                    if(s.hallStudents.get(color) == numStudents && character2Used){
                        s.professors.put(color,false);
                        //need to be called on this school
                        s.notifyObserver();
                    }
                    else earnProfessor = false;
                }else{
                    s.professors.put(color,false);
                    //need to be called on this school
                    s.notifyObserver();
                }
                break; //because the professor can be owned at most by one school
            }
        }
        if(earnProfessor){
            professors.put(color, true);
        }
        notifyObserver();
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
    public void notifyObserver(){ schoolObserver.update();}

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

    public SchoolObserver getSchoolObserver() {
        return schoolObserver;
    }

    public void setCharacter2Used(boolean character2Used) {
        this.character2Used = character2Used;
    }
}
