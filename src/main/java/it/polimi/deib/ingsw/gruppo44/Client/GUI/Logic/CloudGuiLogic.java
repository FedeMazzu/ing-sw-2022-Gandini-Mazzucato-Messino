package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


import java.util.Map;

/**
 * class to model a cloud for the GUI
 */
public class CloudGuiLogic {
    private int cloudId;
    private Map<Color, Label> students;
    private Map<Color,ImageView> studentsSymbols;
    private ImageView cloud;
    private Circle circle;

    public CloudGuiLogic(int cloudId,Map<Color, Label> students, Map<Color, ImageView> studentsSymbols, ImageView cloud, Circle circle) {
        this.cloudId = cloudId;
        this.students = students;
        this.studentsSymbols = studentsSymbols;
        this.cloud = cloud;
        this.circle = circle;
        resizeForCurrentGame();
    }

    public Map<Color, Label> getStudents() {
        return students;
    }

    public Map<Color, ImageView> getStudentsSymbols() {
        return studentsSymbols;
    }

    public ImageView getCloud() {
        return cloud;
    }

    public Circle getCircle() {
        return circle;
    }

    /**
     *  resize the clouds and translate them depending on the gameMode
     */
    private void resizeForCurrentGame(){
        if(Eriantys.getCurrentApplication().getGameMode().getCloudsNumber()==2){
            if(cloudId==0) resize(1,1,-35,50);
            if(cloudId==1) resize(1,1,35,50);
        }else if(Eriantys.getCurrentApplication().getGameMode().getCloudsNumber()==3){
            if(cloudId==2) resize(0.8,0.8,75,-15);
        }
    }

    private void resize(double resizeX, double resizeY, double translateX, double translateY){
        Platform.runLater(()->{
            cloud.setScaleX(resizeX);
            cloud.setScaleY(resizeY);
            cloud.setTranslateX(translateX);
            cloud.setTranslateY(translateY);

            circle.setScaleX(resizeX);
            circle.setScaleY(resizeY);
            circle.setTranslateX(translateX);
            circle.setTranslateY(translateY);

            for(Color c: Color.values()){

                students.get(c).setScaleX(resizeX);
                students.get(c).setScaleY(resizeY);
                students.get(c).setTranslateX(translateX);
                students.get(c).setTranslateY(translateY);

                studentsSymbols.get(c).setScaleX(resizeX);
                studentsSymbols.get(c).setScaleY(resizeY);
                studentsSymbols.get(c).setTranslateX(translateX);
                studentsSymbols.get(c).setTranslateY(translateY);

            }


        });

    }
}
