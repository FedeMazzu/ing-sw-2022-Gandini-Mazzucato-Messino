package it.polimi.deib.ingsw.gruppo44.Common.Messages;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;

import java.io.Serializable;
import java.util.Scanner;

public class MovingStudentsMESSAGE implements Serializable {
    private Color[] movingStudents;
    private boolean[] movingOnIsland;
    private int[] islandID;


    public MovingStudentsMESSAGE(int movingStudentsNum){
        movingStudents = new Color[movingStudentsNum];
        movingOnIsland = new boolean[movingStudentsNum];
        islandID = new int[movingStudentsNum];
    }

    public void moveStudents(){
        Scanner sc = new Scanner(System.in);
        for(int i=0; i< movingStudents.length; i++){
            System.out.println("Choose a color: \n 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
            int colorChoice = sc.nextInt();
            switch (colorChoice){
                case 1:
                    movingStudents[i]= Color.GREEN;
                break;
                case 2:
                    movingStudents[i]= Color.RED;
                    break;
                case 3:
                    movingStudents[i]= Color.YELLOW;
                    break;
                case 4:
                    movingStudents[i]= Color.PINK;
                    break;
                case 5:
                    movingStudents[i]= Color.BLUE;
                    break;
                default:
                    System.out.println("incorrect value");
                    System.exit(0);
            }
            System.out.println("Where do you want to put it? 0:ISLAND || 1:SCHOOL");
            int placeChoice = sc.nextInt();
            if(placeChoice==0){
                movingOnIsland[i] = true;
                System.out.println("On which island?(0-11)");
                islandID[i]= sc.nextInt();

            }else{
                movingOnIsland[i] = false;
            }

        }

    }

    public Color getStudent(int index){
        return movingStudents[index];
    }
    public boolean isOnisland(int index){
        return movingOnIsland[index];
    }
    public int getIslandId(int index){
        return islandID[index];
    }
}
