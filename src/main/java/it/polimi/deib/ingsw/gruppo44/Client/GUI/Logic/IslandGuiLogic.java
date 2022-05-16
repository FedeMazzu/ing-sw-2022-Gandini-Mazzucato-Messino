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
