package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.SchoolGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * class to manage the Schools Scene
 */
public class SchoolsScene2pController implements Initializable {
    @FXML
    private ImageView b1;

    @FXML
    private ImageView b2;

    @FXML
    private ImageView b3;

    @FXML
    private ImageView b4;

    @FXML
    private Button cardsButton;

    @FXML
    private ImageView coin1;

    @FXML
    private ImageView coin2;

    @FXML
    private ImageView coin3;

    @FXML
    private ImageView coin4;

    @FXML
    private Label eb1;

    @FXML
    private Label eb2;

    @FXML
    private Label eb3;

    @FXML
    private Label eb4;

    @FXML
    private Label eg1;

    @FXML
    private Label eg2;

    @FXML
    private Label eg3;

    @FXML
    private Label eg4;

    @FXML
    private Label ep1;

    @FXML
    private Label ep2;

    @FXML
    private Label ep3;

    @FXML
    private Label ep4;

    @FXML
    private Label er1;

    @FXML
    private Label er2;

    @FXML
    private Label er3;

    @FXML
    private Label er4;

    @FXML
    private Label ey1;

    @FXML
    private Label ey2;

    @FXML
    private Label ey3;

    @FXML
    private Label ey4;

    @FXML
    private ImageView g1;

    @FXML
    private ImageView g2;

    @FXML
    private ImageView g3;

    @FXML
    private ImageView g4;

    @FXML
    private Label hb1;

    @FXML
    private Label hb2;

    @FXML
    private Label hb3;

    @FXML
    private Label hb4;

    @FXML
    private Label hg1;

    @FXML
    private Label hg2;

    @FXML
    private Label hg3;

    @FXML
    private Label hg4;

    @FXML
    private Label hp1;

    @FXML
    private Label hp2;

    @FXML
    private Label hp3;

    @FXML
    private Label hp4;

    @FXML
    private Label hr1;

    @FXML
    private Label hr2;

    @FXML
    private Label hr3;

    @FXML
    private Label hr4;

    @FXML
    private Label hy1;

    @FXML
    private Label hy2;

    @FXML
    private Label hy3;

    @FXML
    private Label hy4;

    @FXML
    private Button islandsButton;

    @FXML
    private Label magician1;

    @FXML
    private Label magician2;

    @FXML
    private Label magician3;

    @FXML
    private Label magician4;

    @FXML
    private Label money1;

    @FXML
    private Label money2;

    @FXML
    private Label money3;

    @FXML
    private Label money4;

    @FXML
    private Label name1;

    @FXML
    private Label name2;

    @FXML
    private Label name3;

    @FXML
    private Label name4;

    @FXML
    private Label numTower1;

    @FXML
    private Label numTower2;

    @FXML
    private Label numTower3;

    @FXML
    private Label numTower4;

    @FXML
    private ImageView p1;

    @FXML
    private ImageView p2;

    @FXML
    private ImageView p3;

    @FXML
    private ImageView p4;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView pb1;

    @FXML
    private ImageView pb2;

    @FXML
    private ImageView pb3;

    @FXML
    private ImageView pb4;

    @FXML
    private ImageView pg1;

    @FXML
    private ImageView pg2;

    @FXML
    private ImageView pg3;

    @FXML
    private ImageView pg4;

    @FXML
    private ImageView pp1;

    @FXML
    private ImageView pp2;

    @FXML
    private ImageView pp3;

    @FXML
    private ImageView pp4;

    @FXML
    private ImageView pr1;

    @FXML
    private ImageView pr2;

    @FXML
    private ImageView pr3;

    @FXML
    private ImageView pr4;

    @FXML
    private ImageView py1;

    @FXML
    private ImageView py2;

    @FXML
    private ImageView py3;

    @FXML
    private ImageView py4;

    @FXML
    private ImageView r1;

    @FXML
    private ImageView r2;

    @FXML
    private ImageView r3;

    @FXML
    private ImageView r4;

    @FXML
    private ImageView school1;

    @FXML
    private ImageView school2;

    @FXML
    private ImageView school3;

    @FXML
    private ImageView school4;

    @FXML
    private ImageView tower1;

    @FXML
    private ImageView tower2;

    @FXML
    private ImageView tower3;

    @FXML
    private ImageView tower4;

    @FXML
    private ImageView y1;

    @FXML
    private ImageView y2;

    @FXML
    private ImageView y3;

    @FXML
    private ImageView y4;



    private Map<Magician, SchoolGuiLogic> schoolInfo = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setSchoolsScene2pController(this);

    }

    public void buildDataStructures(){
        Scene currScene = pane.getScene();

        Map<Color,Label> hallStudents, entranceStudents;
        Map<Color,ImageView> prof,colorTokens;
        Label name, magicianLabel,money;
        Label numTower;
        ImageView coin,tower,schoolImage;

        for(Magician magician:Eriantys.getCurrentApplication().getMagicianId().keySet()){
            int mageId = Eriantys.getCurrentApplication().getMagicianId().get(magician);
            hallStudents = new HashMap<>();
            entranceStudents = new HashMap<>();
            prof = new HashMap<>();
            colorTokens = new HashMap<>();

            schoolImage = (ImageView)currScene.lookup("#school"+mageId);
            name = (Label)currScene.lookup("#name"+mageId);
            magicianLabel = (Label)currScene.lookup("#magician"+mageId);
            money = (Label)currScene.lookup("#money"+mageId);
            numTower = (Label)currScene.lookup("#numTower"+mageId);
            coin = (ImageView) currScene.lookup("#coin"+mageId);
            tower = (ImageView) currScene.lookup("#tower"+mageId);

            schoolImage.setVisible(true);
            name.setVisible(true);
            magicianLabel.setVisible(true);
            money.setVisible(true);
            numTower.setVisible(true);
            coin.setVisible(true);
            tower.setVisible(true);

            for(Color color:Color.values()){
                Label tempLabel = (Label)currScene.lookup("#h"+color.getId()+mageId);
                ImageView tempImageview = (ImageView)currScene.lookup("#p"+color.getId()+mageId);
                tempLabel.setVisible(true);
                //hall Students
                hallStudents.put(color,tempLabel);
                //entrance Students
                tempLabel = (Label)currScene.lookup(("#e"+color.getId()+mageId));
                tempLabel.setVisible(true);
                entranceStudents.put(color,tempLabel);
                //professors
                tempImageview.setVisible(true);
                prof.put(color,tempImageview);
                //color Tokens
                tempImageview = (ImageView)currScene.lookup("#"+color.getId()+mageId);
                tempImageview.setVisible(true);
                colorTokens.put(color,tempImageview);
            }


            schoolInfo.put(magician,new SchoolGuiLogic(hallStudents,entranceStudents,prof,colorTokens,name,money,magicianLabel,numTower,coin,tower,schoolImage,mageId));

        }
    }

    public Map<Magician, SchoolGuiLogic> getSchoolInfo() {
        return schoolInfo;
    }


    public void switchToCards(ActionEvent actionEvent) {
        Eriantys.getCurrentApplication().switchToCardsScene();
    }

    public void switchToIslands(ActionEvent actionEvent) {
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }
}
