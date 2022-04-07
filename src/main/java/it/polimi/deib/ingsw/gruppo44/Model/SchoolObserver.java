package it.polimi.deib.ingsw.gruppo44.Model;

/**
 * class to observe the schools
 * @author filippogandini
 */
public class SchoolObserver {
    private School school;

    /**
     * Constructor called form the School constructor
     * @param school observed
     */
    public SchoolObserver(School school){
        this.school = school;
    }

    /**
     * method called for checking the School status after a variation
     */
    public void update(){
    }
}
