package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.SchoolGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private Label name1,magician1,money1,name2,magician2,money2;
    @FXML
    private Label eb1;

    @FXML
    private Label eb2;

    @FXML
    private Label eg1;

    @FXML
    private Label eg2;

    @FXML
    private Label ep1;

    @FXML
    private Label ep2;

    @FXML
    private Label er1;

    @FXML
    private Label er2;

    @FXML
    private Label ey1;

    @FXML
    private Label ey2;

    @FXML
    private Label hb1;

    @FXML
    private Label hb2;

    @FXML
    private Label hg1;

    @FXML
    private Label hg2;

    @FXML
    private Label hp1;

    @FXML
    private Label hp2;

    @FXML
    private Label hr1;

    @FXML
    private Label hr2;

    @FXML
    private Label hy1;

    @FXML
    private Label hy2;

    @FXML
    private ImageView pb1;

    @FXML
    private ImageView pb2;

    @FXML
    private ImageView pg1;

    @FXML
    private ImageView pg2;

    @FXML
    private ImageView pp1;

    @FXML
    private ImageView pp2;

    @FXML
    private ImageView pr1;

    @FXML
    private ImageView pr2;

    @FXML
    private ImageView py1;

    @FXML
    private ImageView py2;

    @FXML
    private AnchorPane pane;


    private Map<Magician, SchoolGuiLogic> schoolInfo = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setSchoolsScene2pController(this);

    }

    public void buildDataStructures(){
        Scene currScene = pane.getScene();

        Map<Color,Label> hallStudents, entranceStudents;
        Map<Color,ImageView> prof;
        Label name, magicianLabel,money;
        Label numTower;
        ImageView coin,tower;

        for(Magician magician:Eriantys.getCurrentApplication().getMagicianId().keySet()){
            int mageId = Eriantys.getCurrentApplication().getMagicianId().get(magician);
            hallStudents = new HashMap<>();
            entranceStudents = new HashMap<>();
            prof = new HashMap<>();

            name = (Label)currScene.lookup("#name"+mageId);
            magicianLabel = (Label)currScene.lookup("#magician"+mageId);
            money = (Label)currScene.lookup("#money"+mageId);
            numTower = (Label)currScene.lookup("#numTower"+mageId);
            coin = (ImageView) currScene.lookup("#coin"+mageId);
            tower = (ImageView) currScene.lookup("#tower"+mageId);

            for(Color color:Color.values()){
                //hall Students
                hallStudents.put(color,(Label)currScene.lookup("#h"+color.getId()+mageId));
                //entrance Students
                entranceStudents.put(color,(Label)currScene.lookup(("#e"+color.getId()+mageId)));
                //professors
                prof.put(color,(ImageView)currScene.lookup("#p"+color.getId()+mageId));
            }


            schoolInfo.put(magician,new SchoolGuiLogic(hallStudents,entranceStudents,prof,name,money,magicianLabel,numTower,coin,tower));

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
