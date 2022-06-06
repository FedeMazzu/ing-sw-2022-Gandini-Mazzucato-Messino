package it.polimi.deib.ingsw.gruppo44.Common.Messages;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;

import java.io.Serializable;

/**
 *  Class to manage the message for a creation of a game
 */
public class CreateGameMESSAGE implements Serializable {
    private String gameName;
    private GameMode gameMode;

    public CreateGameMESSAGE(String gameName, GameMode gameMode) {
        this.gameName = gameName;
        this.gameMode = gameMode;
    }

    public String getGameName() {
        return gameName;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
