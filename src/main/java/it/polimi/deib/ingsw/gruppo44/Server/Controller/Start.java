package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.Data;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;

import java.io.Serializable;
import java.util.*;


/**
 * stage to initialize the game
 * @author
 */
public class Start implements Stage, Serializable {
    private final GameStage gameStage = GameStage.START;
    private final GameController gameController;
    private Game game;
    private Data data;
    private Scanner sc = new Scanner(System.in);
    private Map<Magician,Boolean> freeMagician;
    public Start(GameController gameController) {
        this.gameController = gameController;
        sc = new Scanner(System.in);
        freeMagician = new HashMap<>();
        for(Magician magician : Magician.values()) freeMagician.put(magician,false);
    }

    public void handle(){
        Random rand = new Random();
        GameMode gameMode= gameController.getGameMode();
        game = new Game(gameMode);
        data = new Data();
        gameController.setGame(game);
        gameController.setData(data);
        TurnHandler turnHandler = new TurnHandler(gameMode);
        gameController.setTurnHandler(turnHandler);
        //saving the reference of the islands and clouds Data in the Virtual View
        data.setBoardData(game.getBoard().getBoardObserver().getBoardData());
        data.setIslandsData(game.getBoard().getUnionFind().getIslandsObserver().getIslandsData());
        data.setCloudsData(game.getBoard().getCloudsObserver().getCloudsData());

        User user;
        String IP, name;
        int port;
        Magician magician;
        Player player;
        SchoolData schoolData;
        School school;
        PriorityQueue<Ticket> turnOrder = turnHandler.getTurnOrder();
        for(int i=0; i < gameMode.getTeamsNumber(); i++) {
            for (int j = 0; j < gameMode.getTeamPlayers(); j++) {

                player = game.getTeams().get(i).getPlayers().get(j);


                int randPrio = rand.nextInt(4); //random seed to start the game, 4 is the maximum priority possible
                turnOrder.add(new Ticket(player,randPrio));


            }
        }

        while(!turnOrder.isEmpty()){

            player = turnOrder.peek().player;

            System.out.println("-----------------------------\n" +
                    "Player with priority: "+turnOrder.peek().priority);
            school = player.getSchool();
            schoolData = school.getSchoolObserver().getSchoolData();
            //to initialize the School entrance with random values
            game.getBoard().getNotOwnedObjects().fillEntrance(school);
            //ask input to the user
            //where we look if we don't want duplicate names

            name = askName();
            player.setName(name);

            user = new User(player,askIP(),askPort());
            gameController.addUser(user);

            magician = askMagician(); //we will need the user to talk to the right client
            player.setMagician(magician);
            //
            schoolData.setMagician(magician); //setting the identifier
            data.addSchoolData(schoolData);
            turnHandler.endOfTurn(); //the player is put in the card deque and polled from the prioQ
        }

        //at the end
        gameController.setGameStage(GameStage.PLANNING);
    }



    private String askName(){
        System.out.println("Insert your name:");
        String name = sc.next();
        return name;
    }

    private Magician askMagician(){
        int choice;
        while(true) {
            System.out.println("Select your magician(number):");
            for (Magician magician : Magician.values()) {
                if (!freeMagician.get(magician)) {
                    System.out.println(magician.getId() + " - " + magician.getName());
                }
            }
            choice = sc.nextInt();
            for (Magician magician : Magician.values()) {
                if (choice == magician.getId())
                    if (!freeMagician.get(magician)) {
                        freeMagician.put(magician, true);
                        return magician;
                    }

            }
            System.out.println("Magician already selected.. try again");
        }
    }

    private String askIP(){
        String IP;
        boolean correct;
        while(true) {
            System.out.print("Enter your IP address:\n(convention xxx.xxx.xxx.xxx) -> ");
            IP = sc.next();
            correct=false;
            if(IP.length() == 15){
                correct = true;
                for(int i=0; i<15; i++){
                    if(i==3 || i==7 || i==11){
                        if(IP.charAt(i)!='.') {
                            correct = false;
                        }
                    }else if(IP.charAt(i)<'0' || IP.charAt(i)>'9'){
                        correct = false;
                    }
                }
            }
            if(correct) return IP;
            else System.out.println("Incorrect IP, try again..");

        }
    }
    private int askPort(){
        System.out.println("Enter your port:");
        return sc.nextInt();
    }




}
