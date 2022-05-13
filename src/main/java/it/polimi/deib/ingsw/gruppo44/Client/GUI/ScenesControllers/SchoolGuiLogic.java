package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Map;

public class SchoolGuiLogic {

    public SchoolGuiLogic(Map<Color, Label> hallStudents, Map<Color, Label> entranceStudents, Map<Color, ImageView> prof) {
        this.hallStudents = hallStudents;
        this.entranceStudents = entranceStudents;
        this.prof = prof;
    }

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

}
