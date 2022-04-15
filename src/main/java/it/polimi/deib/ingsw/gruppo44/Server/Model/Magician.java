package it.polimi.deib.ingsw.gruppo44.Server.Model;

/**
 * enum to represent the magicians
 * @author filippogandini
 */
public enum Magician {
    KING("king",1),WITCH("Witch",2),MONK("Monk",3),WIZARD("Wizard",4);
    private String name;
    private int id;

    Magician(String name,int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
