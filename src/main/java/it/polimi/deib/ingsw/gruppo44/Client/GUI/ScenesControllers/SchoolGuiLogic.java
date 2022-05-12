package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Map;

public class SchoolGuiLogic {


    private Map<Color, Label> hallStudents, entranceStudents;
    private Map<Color, ImageView> prof;

    public Map<Color, Label> getHallStudents() {
        return hallStudents;
    }

    public Map<Color, Label> getEntranceStudents() {
        return entranceStudents;
    }

    public Map<Color, ImageView> getProf() {
        return prof;
    }

    public void setHallStudents(Map<Color, Label> hallStudents) {
        this.hallStudents = hallStudents;
    }

    public void setEntranceStudents(Map<Color, Label> entranceStudents) {
        this.entranceStudents = entranceStudents;
    }

    public void setProf(Map<Color, ImageView> prof) {
        this.prof = prof;
    }
}
