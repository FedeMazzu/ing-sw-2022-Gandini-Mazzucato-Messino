package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Map;

public class SchoolGuiLogic {

    private Map<Color, Label> hallStudents, entranceStudents;
    private Map<Color, ImageView> prof;
    private Label name,money,magician;

    public SchoolGuiLogic(Map<Color, Label> hallStudents, Map<Color, Label> entranceStudents, Map<Color, ImageView> prof, Label name, Label money, Label magician) {
        this.hallStudents = hallStudents;
        this.entranceStudents = entranceStudents;
        this.prof = prof;
        this.name = name;
        this.money = money;
        this.magician = magician;
    }

    public Map<Color, Label> getHallStudents() {
        return hallStudents;
    }

    public Map<Color, Label> getEntranceStudents() {
        return entranceStudents;
    }

    public Map<Color, ImageView> getProf() {
        return prof;
    }

    public Label getName() {
        return name;
    }

    public Label getMoney() {
        return money;
    }

    public Label getMagician() {
        return magician;
    }
}
