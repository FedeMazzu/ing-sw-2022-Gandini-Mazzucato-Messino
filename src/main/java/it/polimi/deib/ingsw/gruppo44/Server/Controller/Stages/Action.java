package it.polimi.deib.ingsw.gruppo44.Server.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Common.Messages.MovingStudentsMESSAGE;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameController;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.GameStage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.User;
import it.polimi.deib.ingsw.gruppo44.Server.Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * stage to model the action phase
 * @author
 */
public class Action implements Stage, Serializable {
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
    public void handle() throws IOException, ClassNotFoundException {
        boolean endGame = false;
        System.out.println("--------------ACTION PHASE--------------");
        Player currPlayer;
        User currUser;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        String currData;
        while(!turnOrder.isEmpty()){
            System.out.println("entered here");
            currPlayer = turnOrder.peek().getPlayer();
            currUser = currPlayer.getUser();
            oos = currUser.getOos();
            ois = currUser.getOis();
            School currSchool = currPlayer.getSchool();

            currData = "In your school:\n";
            for(Color c : Color.values()){
                currData +="Student "+c+": "+currSchool.getEntranceStudentsNum(c)+"\n";
            }

            currData+="Islands:\n";

            for(int i=0;i<12;i++){
                if(board.getUnionFind().getGroup(i)!=-1) continue;

                currData+="Island ID: "+i+" ";
                for(Color c: Color.values()){
                    currData+=c+" "+board.getUnionFind().getIsland(i).getStudentNum(c)+"| ";
                }
                currData+="\n";
            }
            oos.writeObject(currData);
            oos.flush();

            //receiving client choices
            MovingStudentsMESSAGE msm = (MovingStudentsMESSAGE) ois.readObject();
            for(int i=0;i<gameController.getGameMode().getCloudStudents();i++){

                if(msm.isOnisland(i)){
                    switch (msm.getStudent(i)){
                        case GREEN:
                            currSchool.removeEntranceStudent(Color.GREEN);
                            board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.GREEN);
                            break;
                        case RED:
                            currSchool.removeEntranceStudent(Color.RED);
                            board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.RED);
                            break;
                        case YELLOW:
                            currSchool.removeEntranceStudent(Color.YELLOW);
                            board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.YELLOW);
                            break;
                        case PINK:
                            currSchool.removeEntranceStudent(Color.PINK);
                            board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.PINK);
                            break;
                        case BLUE:
                            currSchool.removeEntranceStudent(Color.BLUE);
                            board.getUnionFind().getIsland(msm.getIslandId(i)).addStudent(Color.BLUE);
                            break;
                        default:
                            System.out.println("Incorrect value");
                            System.exit(0);
                    }

                }
                else{
                    switch (msm.getStudent(i)){
                        case GREEN:
                            currSchool.addHallStudent(Color.GREEN);
                            break;
                        case RED:
                            currSchool.addHallStudent(Color.RED);
                            break;
                        case YELLOW:
                            currSchool.addHallStudent(Color.YELLOW);
                            break;
                        case PINK:
                            currSchool.addHallStudent(Color.PINK);
                            break;
                        case BLUE:
                            currSchool.addHallStudent(Color.BLUE);
                            break;
                        default:
                            System.out.println("Incorrect value");
                            System.exit(0);
                    }
                }


            }
            //moving mother nature
            oos.writeObject("How many steps? max "+(int)Math.ceil(((double)turnOrder.peek().getPriority()/2.0)));
            oos.flush();

            int stepsChoice = ois.readInt();
            System.out.println("Position before: "+board.getMotherNaturePosition());
            board.moveMotherNature(stepsChoice);
            System.out.println("Position after: "+board.getMotherNaturePosition());

            //choosing cloud
            String cloudsData ="";
            for(int i=0;i< board.getClouds().size();i++){
                cloudsData+="Cloud ID "+i;
                Cloud cloud = board.getClouds().get(i);
                for(Color c : Color.values()){
                    cloudsData+="Student: "+c+" "+cloud.getStudentsNum(c);
                }
            }
            oos.writeObject(cloudsData);
            oos.flush();
            int cloudChosen = ois.readInt();
            board.getClouds().get(cloudChosen).wipeCloud(currPlayer);
            endGame = gameController.checkEndOfGame();
            if(endGame){
                oos.writeBoolean(true);
                oos.flush();
                break;
            }
            oos.writeBoolean(false);
            oos.flush();
            gameController.getTurnHandler().endOfTurn();
        }
        if(endGame) gameController.setGameStage(GameStage.END);
        else gameController.setGameStage(GameStage.CLEANUP);
    }
}
