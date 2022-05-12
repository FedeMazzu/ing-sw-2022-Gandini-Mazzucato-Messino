package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Team;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.SchoolData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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

        Map<Color,Label> hallStudents1, entranceStudents1, hallStudents2, entranceStudents2;
        Map<Color,ImageView> prof1, prof2;

        hallStudents1 = new HashMap<>();
        entranceStudents1 = new HashMap<>();
        hallStudents2 = new HashMap<>();
        entranceStudents2 = new HashMap<>();
        prof1 = new HashMap<>();
        prof2 = new HashMap<>();


        //populate entranceStudents1
        entranceStudents1.put(Color.GREEN,eg1);
        entranceStudents1.put(Color.RED,er1);
        entranceStudents1.put(Color.YELLOW,ey1);
        entranceStudents1.put(Color.PINK,ep1);
        entranceStudents1.put(Color.BLUE,eb1);

        //populate entranceStudents2
        entranceStudents2.put(Color.GREEN,eg2);
        entranceStudents2.put(Color.RED,er2);
        entranceStudents2.put(Color.YELLOW,ey2);
        entranceStudents2.put(Color.PINK,ep2);
        entranceStudents2.put(Color.BLUE,eb2);

        //populate hallStudents1
        hallStudents1.put(Color.GREEN,hg1);
        hallStudents1.put(Color.RED,hr1);
        hallStudents1.put(Color.YELLOW,hy1);
        hallStudents1.put(Color.PINK,hp1);
        hallStudents1.put(Color.BLUE,hb1);

        //populate hallStudents2
        hallStudents2.put(Color.GREEN,hg2);
        hallStudents2.put(Color.RED,hr2);
        hallStudents2.put(Color.YELLOW,hy2);
        hallStudents2.put(Color.PINK,hp2);
        hallStudents2.put(Color.BLUE,hb2);

        //populate prof1
        prof1.put(Color.GREEN,pg1);
        prof1.put(Color.RED,pr1);
        prof1.put(Color.YELLOW,py1);
        prof1.put(Color.PINK,pp1);
        prof1.put(Color.BLUE,pb1);

        //populate prof1
        prof2.put(Color.GREEN,pg2);
        prof2.put(Color.RED,pr2);
        prof2.put(Color.YELLOW,py2);
        prof2.put(Color.PINK,pp2);
        prof2.put(Color.BLUE,pb2);

        SchoolGuiLogic schoolGuiLogic1 = new SchoolGuiLogic();
        SchoolGuiLogic schoolGuiLogic2 = new SchoolGuiLogic();

        schoolGuiLogic1.setEntranceStudents(entranceStudents1);
        schoolGuiLogic1.setHallStudents(hallStudents1);
        schoolGuiLogic1.setProf(prof1);

        schoolGuiLogic2.setEntranceStudents(entranceStudents2);
        schoolGuiLogic2.setHallStudents(hallStudents2);
        schoolGuiLogic2.setProf(prof2);



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


}
