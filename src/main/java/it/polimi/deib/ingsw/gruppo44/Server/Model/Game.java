package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the game
 * @author filippogandini
 */
public class Game implements Serializable {
    private List<Team> teams;
    private Board board;

    /**
     * Constructor which initializes the game
     * @param gameMode
     */
    public Game(GameMode gameMode){
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
     * saves the current game on a file
     * @param fileName
     */
    public void saveGame(String fileName){
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
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
     * @param fileName
     * @return the game deserialized
     */
    public static Game loadGame(String fileName){
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
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
