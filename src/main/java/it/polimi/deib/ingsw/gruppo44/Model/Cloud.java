package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.Map;

/**
 * class to manage the clouds
 * @author filippogandini
 */

public class Cloud {
    private Map<Color, Integer> students;
    private boolean empty;
    private int sizeMod;

    public boolean isEmpty() {
        return empty;
    }
}
