package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * class made to observe the mother nature position and the Shop/Characters
 * @author filippogandini
 */
public class BoardData implements Serializable {
    private int motherNaturePosition;
    // Map<CharacterId, CharacterPrice>
    private Map<Integer,Integer> characters;

    public BoardData() {
        characters = new HashMap<>();
        //needed
        this.motherNaturePosition =0;
    }

    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    /**
     * initializes the Map and updates the price
     * @param id
     * @param price
     */
    public void putCharacter(int id, int price){
        characters.put(id,price);
    }

    public Map<Integer, Integer> getCharacters() {
        return characters;
    }
}
