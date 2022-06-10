package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.Map;

public class IslandGuiLogic {
    private Map<Color, Label> students;
    private Map<Color, ImageView> studentsSymbols;
    private ImageView island;
    private Circle circle;
    private ImageView tower;
    private ImageView motherNature;
    private Label numTowers;
    private boolean isCovered;

    public IslandGuiLogic(Map<Color, Label> students, Map<Color, ImageView> studentsSymbols, ImageView island, Circle circle, ImageView tower, ImageView motherNature, Label numTowers) {
        this.students = students;
        this.studentsSymbols = studentsSymbols;
        this.island = island;
        this.circle = circle;
        this.tower = tower;
        this.motherNature = motherNature;
        this.numTowers = numTowers;
        this.isCovered = false;
    }

    public void resizeItems(int sizeOfIsland){
        //we must resize based ONLY on the number of island that make up the bigger island
        //(so that it stays a fixed size during a turn)
        //maybe circle has to be resized more carefully???
        double resFactor = (1.0+(Math.min(sizeOfIsland*0.02,0.1)));

        double newIslandHeight = 210.0 * resFactor;
        double newIslandWidth = 213.0 * resFactor;
        double newCircleRadius = 87 * resFactor;
        double newStudentSize = 29 * resFactor;
        //double newStudLabelWidth = 28 *resFactor;
        //double newStudLabelHeight = 17.6 * resFactor;


        island.setFitHeight(newIslandHeight);
        island.setFitWidth(newIslandWidth);
        circle.setRadius(newCircleRadius);
        for(Color color:Color.values()){
            studentsSymbols.get(color).setFitWidth(newStudentSize);
            studentsSymbols.get(color).setFitHeight(newStudentSize);
            //how to resize labels correctly if needed at all?
        }




    }

    public void transform(int islandSeqNum, double angle,int numOfIslands){
        //given the sequence number we want to draw the island correctly
        //inside an ellipse which has a and b as dimensions
        int a = 890/2;
        int b = 470/2;
        double angOffset = 0.0; //offset of a particular island in degrees
        double totAngle = islandSeqNum*angle;
        //REMEMBER THE WORK
        /*double tempAng = (totAngle*3.0)*Math.PI/180;
                double signS = 1.0,signC = 1.0;
                if(totAngle == 150 || totAngle == 210) signS = -1.0;
                if(totAngle == 60 || totAngle == 120) signC = -1.0;
                angOffsetS = (10*Math.sin(tempAng)*signS);
                angOffsetC = (8.5*Math.cos(tempAng)*signC);

                if(Math.abs(angOffsetS-0.0)<1.0) angOffsetS = 0;
                if(Math.abs(angOffsetC-0.0)<1.0) angOffsetC = 0;*/


        if(numOfIslands == 12){
            double [] offSet = {0,10,7.5,0,-7.5,-10,0,10,7.5,0,-7.5,-10};
            //                  0  1   2 3    4   5 6  7   8 9   10  11
            angOffset = offSet[islandSeqNum];
        }
        else if(numOfIslands == 11){
            double [] offSet = {0,5,5,0,-5,-5,5,5,0,-3,-5};
            //                  0 1 2 3  4  5 6 7 8  9 10
            angOffset = offSet[islandSeqNum];
        }
        else if(numOfIslands == 10){
            double [] offSet = {0,7,0,0,-7,0,7,0,0,-7};
            //                  0 1 2 3 4  5 6 7 8  9
            angOffset = offSet[islandSeqNum];
        }
        else if(numOfIslands == 9){
            double [] offSet = {0,0,0,0,-3,3,0,0,0};
            //                  0 1 2 3  4 5 6 7 8
            angOffset = offSet[islandSeqNum];
        }

        //angle in radians
        totAngle = ((totAngle+angOffset)*Math.PI)/180;



        double newPosX = a*Math.cos(totAngle)+a;
        double newPosY = b*Math.sin(totAngle)+b;
        double translCoeffX = newPosX-island.getLayoutX();
        double translCoeffY = newPosY-island.getLayoutY();

        //set islands position
        island.setLayoutY(newPosY);
        island.setLayoutX(newPosX);

        //translate motherNature
        motherNature.setTranslateX(motherNature.getTranslateX()+translCoeffX);
        motherNature.setTranslateY(motherNature.getTranslateY()+translCoeffY);

        //translate circle
        circle.setTranslateX(circle.getTranslateX()+translCoeffX);
        circle.setTranslateY(circle.getTranslateY()+translCoeffY);

        //translate tower
        tower.setTranslateX(tower.getTranslateX()+translCoeffX);
        tower.setTranslateY(tower.getTranslateY()+translCoeffY);

        //translate tower label
        numTowers.setTranslateX(numTowers.getTranslateX()+translCoeffX);
        numTowers.setTranslateY(numTowers.getTranslateY()+translCoeffY);

        //translate students and students label
        for(Color color:Color.values()){
            studentsSymbols.get(color).setTranslateX(studentsSymbols.get(color).getTranslateX()+translCoeffX);
            studentsSymbols.get(color).setTranslateY(studentsSymbols.get(color).getTranslateY()+translCoeffY);
            students.get(color).setTranslateX(students.get(color).getTranslateX()+translCoeffX);
            students.get(color).setTranslateY(students.get(color).getTranslateY()+translCoeffY);
        }
    }

    public void setAllInvisible(){
        island.setVisible(false);
        circle.setVisible(false);
        tower.setVisible(false);
        motherNature.setVisible(false);
        numTowers.setVisible(false);
        for(Color color:Color.values()){
            students.get(color).setVisible(false);
            studentsSymbols.get(color).setVisible(false);
        }
    }

    public void coverMergedIsland(){
        setAllInvisible();
        isCovered = true;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public Map<Color, Label> getStudents() {
        return students;
    }

    public Map<Color, ImageView> getStudentsSymbols() {
        return studentsSymbols;
    }

    public ImageView getIsland() {
        return island;
    }

    public Circle getCircle() {
        return circle;
    }

    public ImageView getTower() {
        return tower;
    }

    public ImageView getMotherNature() {
        return motherNature;
    }

    public Label getNumTowers() {
        return numTowers;
    }
}
