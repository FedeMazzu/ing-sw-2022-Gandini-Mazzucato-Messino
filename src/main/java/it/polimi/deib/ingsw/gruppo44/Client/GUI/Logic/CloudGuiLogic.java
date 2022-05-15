package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


import java.util.Map;

public class CloudGuiLogic {
    private Map<Color, Label> students;
    private ImageView cloud;
    private Circle circle;

    public CloudGuiLogic(Map<Color, Label> students, ImageView cloud, Circle circle) {
        this.students = students;
        this.cloud = cloud;
        this.circle = circle;
    }

    public Map<Color, Label> getStudents() {
        return students;
    }

    public ImageView getCloud() {
        return cloud;
    }

    public Circle getCircle() {
        return circle;
    }
}
