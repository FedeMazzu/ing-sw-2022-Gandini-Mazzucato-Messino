package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * class to manage the games
 * Note that it manages the game controllers, each controller is associated
 * to exactly one game
 */
public class GamesManager implements Serializable {
    private Map<String,GameController> games;
    Scanner sc = new Scanner(System.in);


    public GamesManager(){
        this.games = new HashMap<>();
    }

    /**
     * Creates and runs the game
     */
    public void createGame(String gameName, GameMode gameMode, User creatorUser){
        GameController gameController = new GameController(gameName,gameMode);
        gameController.addUser(creatorUser);
        games.put(gameName,gameController);
        new Thread(gameController).start();
    }


    public boolean joinGame(String gameName,User joiningUser){
        GameController gameController = games.get(gameName);
        return gameController.addUser(joiningUser);
        //note that this just has to add the user to the appropriate GameController
    }


    /**
     * loads a game from a file .ser and runs it
      * @param gameName
     */
    public void loadGame(String gameName, User user) throws IOException, ClassNotFoundException {
        //reinitialize because if a game can be loaded it has already been created and added to games
        GameController gameController = GameController.loadGame(gameName);
        //we mark the game as loaded to go into a special planning
        gameController.setLoadedGame(true);
        games.put(gameController.getGameName(),gameController);
        new Thread(gameController).start();

        magicianAuthentication(gameName,user);

    }



    public List<String> getLoadableGames() throws IOException {
        //correct the path
        List <File> filesInFolder = Files.list(Paths.get("savedGames"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());



        List <String> loadableGames = new ArrayList<>();
        for(File file:filesInFolder){
            loadableGames.add(file.getName().replaceAll(".ser",""));
        }
        return loadableGames;
    }

    public void joinLoadedGame(String gameName,User user) throws IOException, ClassNotFoundException {
        magicianAuthentication(gameName,user);
    }



    /**
     * @return the name and gameMode of the running games
     */
    public Map<String,GameMode> getOpenGames(){
        Map<String,GameMode> openGames = new HashMap<>();
        for(String s : games.keySet()){
            if(games.get(s).getGameIsFull()) continue;
            openGames.put(s, games.get(s).getGameMode());
        }
        return openGames;
    }


    public List<String> getLoadedOpenGames() {
        List<String> loadedOpenGames = new ArrayList<>();
        for(String s : games.keySet()){
            if(games.get(s).isLoadedGame()) loadedOpenGames.add(s);
        }
        return loadedOpenGames;
    }

    private  void magicianAuthentication(String gameName, User user) throws IOException, ClassNotFoundException {
        Map<Magician,Player> usedMagicians = new HashMap<>();
        List<Magician> magicians = new ArrayList<>();
        Game game = games.get(gameName).getGame();
        for(Team t: game.getTeams()){
            for(Player p: t.getPlayers()){
                usedMagicians.put(p.getMagician(),p);
                magicians.add(p.getMagician());
            }
        }
        ObjectOutputStream oos = user.getOos();
        ObjectInputStream ois = user.getOis();
        oos.writeObject(magicians);
        oos.flush();
        //receiving the magician used
        Magician magician = (Magician) ois.readObject();

        //associating the player to the user
        Player player = usedMagicians.get(magician);
        user.setPlayer(player);
        player.setUser(user);
        //waking up the thread in the planning stage
        GameController gameController = games.get(gameName);
        gameController.addUser(user);
        synchronized (gameController) {
            games.get(gameName).notifyAll();
        }

    }
}
