package it.polimi.deib.ingsw.gruppo44.Model;

/**
 * enum to represent the towers type
 * @author filippogandini
 */
public enum Tower {
    BLACK(0),WHITE(1),GREY(2);
    private int id;

    Tower(int id) {
        this.id = id;
    }
    /**
     * Naive method which returns the instance associated to the passed id
     * @param id of the instance
     */
    public static Tower getColorById(int id) {
        if(id == 0) return BLACK;
        if(id == 1) return WHITE;
        else return GREY;
        //GREY is related to the two value, but for safety we write the method this way
    }
}
