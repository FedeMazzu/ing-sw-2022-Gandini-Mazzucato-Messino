package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the game
 * the name is the identifier
 * @author filippogandini
 */
public class Game implements Serializable {
    private final String name; //identifier
    private List<Team> teams;
    private Board board;

    /**
     * Constructor which initializes the game
     * @param gameMode
     */
    public Game(String name, GameMode gameMode){
        this.name = name;
        teams = new ArrayList<>();
        // the index i is necessary to count the initialized teams
        int i=0;
        board = new Board(gameMode,this);
        for(Tower tower : Tower.values()){
            if(i == gameMode.getTeamsNumber()) break;
            teams.add(new Team(tower, gameMode,this));
            i++;
        }

    }

    /**
     * saves the current game on a file named <GameName>.ser
     */
    public void saveGame(){
        try {
            FileOutputStream fileOut = new FileOutputStream(name + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            //needed?
            // out.flush();
            out.close();
            fileOut.close();
        }catch (IOException ioe){
           ioe.printStackTrace();
        }
    }

    /**
     * loads the serialized game
     * @param gameName
     * @return the game deserialized
     */
    public static Game loadGame(String gameName){
        try {
            FileInputStream fileIn = new FileInputStream(gameName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Game game = (Game) in.readObject();

            //needed?
            // in.flush();
            in.close();
            fileIn.close();
            return game;
        }catch (IOException | ClassNotFoundException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

    public Board getBoard(){
        return board;
    }

    public List <Team> getTeams(){
        return teams;
    }


}
