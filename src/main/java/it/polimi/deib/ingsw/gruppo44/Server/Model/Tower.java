package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.Serializable;

/**
 * enum to represent the towers type
 */
public enum Tower implements Serializable {
    BLACK("black"),WHITE("white"),GREY("grey");
    private String Id;

    private Tower(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }
}
