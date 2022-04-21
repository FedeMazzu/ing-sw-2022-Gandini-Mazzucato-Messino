package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.Serializable;

/**
 * Enum to represent the possible types of games
 * @author filippogandini
 */
public enum GameMode implements Serializable {
    TwoPlayersBasic(2,1,8,7,3,2,false),
    TwoPlayersExpert(2,1,8,7,3,2,true),
    ThreePlayersBasic(3,1,6,9,4,3,false),
    ThreePlayersExpert(3,1,6,9,4,3,true),
    TeamBasic(2,2,8,7,3,4,false),
    TeamExpert(2,2,8,7,3,4,true);

    private final int teamsNumber;
    private final int teamPlayers;
    private final int teamTowers;
    private final int playerEntranceStudents;
    private final int cloudStudents;
    private final int cloudsNumbers;
    private final boolean expertMode;

    GameMode(int teamsNumber, int teamPlayers, int teamTowers, int playerEntranceStudents, int cloudStudents, int cloudsNumbers, boolean expertMode) {
        this.teamsNumber = teamsNumber;
        this.teamPlayers = teamPlayers;
        this.teamTowers = teamTowers;
        this.playerEntranceStudents = playerEntranceStudents;
        this.cloudStudents = cloudStudents;
        this.cloudsNumbers = cloudsNumbers;
        this.expertMode = expertMode;
    }

    public int getTeamsNumber() {
        return teamsNumber;
    }

    public int getTeamPlayers() {
        return teamPlayers;
    }

    public int getTeamTowers() {
        return teamTowers;
    }

    public int getPlayerEntranceStudents() {
        return playerEntranceStudents;
    }

    public int getCloudStudents() {
        return cloudStudents;
    }

    public int getCloudsNumber() {
        return cloudsNumbers;
    }

    public boolean isExpertMode() {
        return expertMode;
    }
}