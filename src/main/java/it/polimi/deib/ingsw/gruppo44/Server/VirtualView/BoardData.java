package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import java.io.Serializable;

/**
 * class made to observe the mother nature position (and maybe the Shop)
 * @author filippogandini
 */
public class BoardData implements Serializable {
    private int motherNaturePosition;

    public BoardData() {
    }

    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }
}
