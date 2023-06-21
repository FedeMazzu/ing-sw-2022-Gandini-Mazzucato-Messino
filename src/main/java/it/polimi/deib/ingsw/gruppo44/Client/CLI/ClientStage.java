package it.polimi.deib.ingsw.gruppo44.Client.CLI;

/**
 * Enum to represent the client stages
 */
public enum ClientStage {
    CREATE_GAME,
    JOIN_GAME,
    LOAD_GAME,
    SETUP,
    WAITING_BEFORE_TURN,
    WAITING_AFTER_TURN,
    CLIENT_ACTION,
    CLIENT_PLANNING,
    CLIENT_END
}
