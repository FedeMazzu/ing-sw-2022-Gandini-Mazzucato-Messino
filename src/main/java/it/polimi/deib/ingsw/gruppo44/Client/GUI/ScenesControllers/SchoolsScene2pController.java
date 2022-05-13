package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.TemporalQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SchoolsScene2pController implements Initializable {

    private Map<Magician,SchoolGuiLogic> schoolInfo = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Scene currScene = pane.getScene();


        Eriantys.getCurrentApplication().setSchoolsScene2pController(this);

        Map<Color,Label> hallStudents, entranceStudents;
        Map<Color,ImageView> prof;

        hallStudents = new HashMap<>();
        entranceStudents = new HashMap<>();
        prof = new HashMap<>();


        for(Magician magician:Eriantys.getCurrentApplication().getMagicianId().keySet()){
            int mageId = Eriantys.getCurrentApplication().getMagicianId().get(magician);

            for(Color color:Color.values()){
                //hall Students
                hallStudents.put(color,(Label)currScene.lookup("h"+color.getId()+mageId));
                //entrance Students
                entranceStudents.put(color,(Label)currScene.lookup(("e"+color.getId()+mageId)));
                //professors
                prof.put(color,(ImageView)currScene.lookup("p"+color.getId()+mageId));
            }

            schoolInfo.put(magician,new SchoolGuiLogic(hallStudents,entranceStudents,prof));

        }

    }

    public Map<Magician, SchoolGuiLogic> getSchoolInfo() {
        return schoolInfo;
    }


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


}
