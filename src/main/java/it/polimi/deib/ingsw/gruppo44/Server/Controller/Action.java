package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.*;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Action implements Stage {
    private final GameStage gameStage = GameStage.ACTION;
    private final GameController gameController;
    private PriorityQueue<Ticket> turnOrder;
    private Scanner sc = new Scanner(System.in);
    private Board board;

    public Action(GameController gameController) {
        this.gameController = gameController;
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
        this.board = gameController.getGame().getBoard();
    }

    @Override
    public void handle() {
        while(!turnOrder.isEmpty()){
            Player currPlayer = turnOrder.peek().player;
            System.out.println("IT'S THE TURN OF: "+currPlayer.getMagician());
            School currSchool = currPlayer.getSchool();

            //moving students
            for(int i=0;i<gameController.getGameMode().getCloudStudents();i++){
                for(Color c : Color.values()){
                    System.out.println("Student "+c+": "+currSchool.getEntranceStudentsNum(c));
                }
                System.out.println("Choose a color: \n 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        currSchool.addHallStudent(Color.GREEN);
                        break;
                    case 2:
                        currSchool.addHallStudent(Color.RED);
                        break;
                    case 3:
                        currSchool.addHallStudent(Color.YELLOW);
                        break;
                    case 4:
                        currSchool.addHallStudent(Color.PINK);
                        break;
                    case 5:
                        currSchool.addHallStudent(Color.BLUE);
                        break;
                    default:
                        System.out.println("Incorrect value");
                }
            }
            //moving mother nature
            System.out.println("Position before: "+board.getMotherNaturePosition());
            board.moveMotherNature((int) Math.ceil((double)((double)turnOrder.peek().priority/2.0))); //max movement possible for testing purpose
            System.out.println("Position after: "+board.getMotherNaturePosition());

            //choosing cloud
            for(int i=0;i< board.getClouds().size();i++){
                System.out.println("Cloud ID "+i);
                Cloud cloud = board.getClouds().get(i);
                for(Color c : Color.values()){
                    System.out.println("Student: "+c+" "+cloud.getStudentsNum(c));
                }
            }
            System.out.println("Choose a cloud ID: ");
            int cloudChosen = sc.nextInt();
            board.getClouds().get(cloudChosen).wipeCloud(currPlayer);

            for(Color c : Color.values()){
                System.out.println("Student "+c+": "+currSchool.getEntranceStudentsNum(c));
            }

            for(int i=0;i< board.getClouds().size();i++){
                System.out.println("Cloud ID "+i);
                Cloud cloud = board.getClouds().get(i);
                for(Color c : Color.values()){
                    System.out.println("Student: "+c+" "+cloud.getStudentsNum(c));
                }
            }

            gameController.getTurnHandler().endOfTurn();
        }
    }
}
