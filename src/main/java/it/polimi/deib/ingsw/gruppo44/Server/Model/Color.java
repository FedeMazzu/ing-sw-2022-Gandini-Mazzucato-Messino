package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.Serializable;

/**
 * enum to represent the students/professors colors
 * @author filippogandini
 */
public enum Color implements Serializable {
    GREEN('g'),RED('r'),YELLOW('y'),PINK('p'),BLUE('b');

    private char id;

    Color(char id){
        this.id = id;
    }

    public char getId() {
        return id;
    }
}
