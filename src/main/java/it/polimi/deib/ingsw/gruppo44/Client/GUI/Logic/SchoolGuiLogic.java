package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Map;

public class SchoolGuiLogic {

    private Map<Color, Label> hallStudents, entranceStudents;
    private Map<Color,ImageView> colorTokens;
    private Map<Color, ImageView> prof;
    private Label name,money,magician,numTower;
    private ImageView coin,tower,school;
    private int schoolId;

    public SchoolGuiLogic(Map<Color, Label> hallStudents, Map<Color, Label> entranceStudents, Map<Color, ImageView> prof, Map<Color,ImageView> colorTokens, Label name, Label money, Label magician, Label numTower, ImageView coin, ImageView tower, ImageView schoolImage, int schoolId) {
        this.hallStudents = hallStudents;
        this.entranceStudents = entranceStudents;
        this.prof = prof;
        this.name = name;
        this.money = money;
        this.magician = magician;
        this.numTower = numTower;
        this.coin = coin;
        this.tower = tower;
        this.colorTokens = colorTokens;
        this.schoolId = schoolId;
        this.school = schoolImage ;
        resizeForCurrentGame();
    }

    private void resizeForCurrentGame(){

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

    public Label getNumTower() {
        return numTower;
    }

    public ImageView getCoin() {
        return coin;
    }

    public ImageView getTower() {
        return tower;
    }
}
