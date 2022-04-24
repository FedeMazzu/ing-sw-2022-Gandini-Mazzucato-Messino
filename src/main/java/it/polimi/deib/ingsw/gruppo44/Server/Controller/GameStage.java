package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import java.io.Serializable;

/**
 * Enum to define the different Game stages
 * @author filippogandini
 */
public enum GameStage implements Serializable {
    START, PLANNING, ACTION, CLEANUP ,END;
}
