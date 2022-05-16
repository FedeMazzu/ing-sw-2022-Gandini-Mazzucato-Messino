package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


import java.util.Map;

public class CloudGuiLogic {
    private Map<Color, Label> students;
    private Map<Color,ImageView> studentsSymbols;
    private ImageView cloud;
    private Circle circle;

    public CloudGuiLogic(Map<Color, Label> students, Map<Color, ImageView> studentsSymbols, ImageView cloud, Circle circle) {
        this.students = students;
        this.studentsSymbols = studentsSymbols;
        this.cloud = cloud;
        this.circle = circle;
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
}
