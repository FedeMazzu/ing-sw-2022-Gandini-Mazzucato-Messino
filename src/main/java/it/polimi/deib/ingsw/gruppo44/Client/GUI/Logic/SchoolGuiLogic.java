package it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Map;
/**
 * class to model a school for the GUI
 */
public class SchoolGuiLogic {

    private Rectangle rect;
    private Map<Color, Label> hallStudents, entranceStudents;
    private Map<Color,ImageView> colorTokens;
    private Map<Color, ImageView> prof;
    private Label name,money,magician,numTower;
    private ImageView coin,tower,school;
    private int schoolId;

    public SchoolGuiLogic(Rectangle rect,Map<Color, Label> hallStudents, Map<Color, Label> entranceStudents, Map<Color, ImageView> prof, Map<Color,ImageView> colorTokens, Label name, Label money, Label magician, Label numTower, ImageView coin, ImageView tower, ImageView schoolImage, int schoolId) {
        this.rect = rect;
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
        if (Eriantys.getCurrentApplication().getGameMode().getCloudsNumber()==2){
            if(schoolId ==1) translate(200,100);
            if(schoolId ==2) translate(550,-251);

        }else if(Eriantys.getCurrentApplication().getGameMode().getCloudsNumber()==3){
            if(schoolId ==1) translate(50,100);
            if(schoolId ==2) translate(400,-251);
            if(schoolId ==3) translate(-50,100);
        }

    }
    private void translate(double x, double y){
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        name.setTranslateX(x);
        name.setTranslateY(y);
        money.setTranslateX(x);
        money.setTranslateY(y);
        magician.setTranslateX(x);
        magician.setTranslateY(y);
        numTower.setTranslateX(x);
        numTower.setTranslateY(y);
        coin.setTranslateX(x);
        coin.setTranslateY(y);
        tower.setTranslateX(x);
        tower.setTranslateY(y);
        school.setTranslateX(x);
        school.setTranslateY(y);
        for(Color c : Color.values()){
            hallStudents.get(c).setTranslateX(x);
            hallStudents.get(c).setTranslateY(y);
            entranceStudents.get(c).setTranslateX(x);
            entranceStudents.get(c).setTranslateY(y);
            prof.get(c).setTranslateX(x);
            prof.get(c).setTranslateY(y);
            colorTokens.get(c).setTranslateX(x);
            colorTokens.get(c).setTranslateY(y);
        }
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

    public Rectangle getRect() {
        return rect;
    }
}
