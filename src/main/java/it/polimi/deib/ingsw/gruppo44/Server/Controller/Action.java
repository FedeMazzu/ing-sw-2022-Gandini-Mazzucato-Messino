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
        System.out.println("--------------ACTION PHASE--------------");
        while(!turnOrder.isEmpty()){
            Player currPlayer = turnOrder.peek().player;
            System.out.println("IT'S THE TURN OF: "+currPlayer.getMagician());
            School currSchool = currPlayer.getSchool();

            System.out.println("In your school: ");
            for(Color c : Color.values()){
                System.out.println("Student "+c+": "+currSchool.getEntranceStudentsNum(c));
            }

            System.out.println("Islands: ");

            for(int i=0;i<12;i++){
                if(board.getUnionFind().getGroup(i)!=-1) continue;
                System.out.print("Island ID: "+i+" ");
                for(Color c: Color.values()){
                    System.out.print(c+" "+board.getUnionFind().getIsland(i).getStudentNum(c)+" ");
                }
                System.out.println(" ");
            }

            //moving students
            for(int i=0;i<gameController.getGameMode().getCloudStudents();i++){

                System.out.println("Choose a color: \n 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
                int colorChoice = sc.nextInt();
                System.out.println("Where do you want to put it? 0:ISLAND -- 1:SCHOOL");
                int placeChoice = sc.nextInt();

                if(placeChoice == 0){
                    System.out.println("On which Island?");
                    int islandChoice = sc.nextInt();
                    switch (colorChoice){
                        case 1:
                            currSchool.removeEntranceStudent(Color.GREEN);
                            board.getUnionFind().getIsland(islandChoice).addStudent(Color.GREEN);
                            break;
                        case 2:
                            currSchool.removeEntranceStudent(Color.RED);
                            board.getUnionFind().getIsland(islandChoice).addStudent(Color.RED);
                            break;
                        case 3:
                            currSchool.removeEntranceStudent(Color.YELLOW);
                            board.getUnionFind().getIsland(islandChoice).addStudent(Color.YELLOW);
                            break;
                        case 4:
                            currSchool.removeEntranceStudent(Color.PINK);
                            board.getUnionFind().getIsland(islandChoice).addStudent(Color.PINK);
                            break;
                        case 5:
                            currSchool.removeEntranceStudent(Color.BLUE);
                            board.getUnionFind().getIsland(islandChoice).addStudent(Color.BLUE);
                            break;
                        default:
                            System.out.println("Incorrect value");
                    }

                }
                else{
                    switch (colorChoice){
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


            }
            //moving mother nature
            System.out.println("How many steps? max "+Math.ceil(((double)turnOrder.peek().priority/2.0)));
            int stepsChoice = sc.nextInt();
            System.out.println("Position before: "+board.getMotherNaturePosition());
            board.moveMotherNature(stepsChoice);
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

            /*for(Color c : Color.values()){
                System.out.println("Student "+c+": "+currSchool.getEntranceStudentsNum(c));
            }

            for(int i=0;i< board.getClouds().size();i++){
                System.out.println("Cloud ID "+i);
                Cloud cloud = board.getClouds().get(i);
                for(Color c : Color.values()){
                    System.out.println("Student: "+c+" "+cloud.getStudentsNum(c));
                }
            }*/
            //check end of game conditions
            gameController.getTurnHandler().endOfTurn();

        }
        gameController.setGameStage(GameStage.CLEANUP);
    }
}
