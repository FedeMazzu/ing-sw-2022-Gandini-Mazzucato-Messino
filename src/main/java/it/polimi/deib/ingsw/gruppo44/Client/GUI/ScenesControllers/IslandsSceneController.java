package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;


import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * class to manage the islands GUI
 */
public class IslandsSceneController implements Initializable {

    private Map<Integer, IslandGuiLogic> islands;

    @FXML
    private Label b0;

    @FXML
    private Label b1;

    @FXML
    private Label b10;

    @FXML
    private Label b11;

    @FXML
    private Label b2;

    @FXML
    private Label b3;

    @FXML
    private Label b4;

    @FXML
    private Label b5;

    @FXML
    private Label b6;

    @FXML
    private Label b7;

    @FXML
    private Label b8;

    @FXML
    private Label b9;

    @FXML
    private Circle c0;

    @FXML
    private Circle c1;

    @FXML
    private Circle c10;

    @FXML
    private Circle c11;

    @FXML
    private Circle c2;

    @FXML
    private Circle c3;

    @FXML
    private Circle c4;

    @FXML
    private Circle c5;

    @FXML
    private Circle c6;

    @FXML
    private Circle c7;

    @FXML
    private Circle c8;

    @FXML
    private Circle c9;

    @FXML
    private Button cardsButton;

    @FXML
    private Label g0;

    @FXML
    private Label g1;

    @FXML
    private Label g10;

    @FXML
    private Label g11;

    @FXML
    private Label g2;

    @FXML
    private Label g3;

    @FXML
    private Label g4;

    @FXML
    private Label g5;

    @FXML
    private Label g6;

    @FXML
    private Label g7;

    @FXML
    private Label g8;

    @FXML
    private Label g9;

    @FXML
    private ImageView i0;

    @FXML
    private ImageView i1;

    @FXML
    private ImageView i10;

    @FXML
    private ImageView i11;

    @FXML
    private ImageView i2;

    @FXML
    private ImageView i3;

    @FXML
    private ImageView i4;

    @FXML
    private ImageView i5;

    @FXML
    private ImageView i6;

    @FXML
    private ImageView i7;

    @FXML
    private ImageView i8;

    @FXML
    private ImageView i9;

    @FXML
    private ImageView m0;

    @FXML
    private ImageView m1;

    @FXML
    private ImageView m10;

    @FXML
    private ImageView m11;

    @FXML
    private ImageView m2;

    @FXML
    private ImageView m3;

    @FXML
    private ImageView m4;

    @FXML
    private ImageView m5;

    @FXML
    private ImageView m6;

    @FXML
    private ImageView m7;

    @FXML
    private ImageView m8;

    @FXML
    private ImageView m9;

    @FXML
    private Label numT0;

    @FXML
    private Label numT1;

    @FXML
    private Label numT10;

    @FXML
    private Label numT11;

    @FXML
    private Label numT2;

    @FXML
    private Label numT3;

    @FXML
    private Label numT4;

    @FXML
    private Label numT5;

    @FXML
    private Label numT6;

    @FXML
    private Label numT7;

    @FXML
    private Label numT8;

    @FXML
    private Label numT9;

    @FXML
    private Label p0;

    @FXML
    private Label p1;

    @FXML
    private Label p10;

    @FXML
    private Label p11;

    @FXML
    private Label p2;

    @FXML
    private Label p3;

    @FXML
    private Label p4;

    @FXML
    private Label p5;

    @FXML
    private Label p6;

    @FXML
    private Label p7;

    @FXML
    private Label p8;

    @FXML
    private Label p9;

    @FXML
    private Label r0;

    @FXML
    private Label r1;

    @FXML
    private Label r10;

    @FXML
    private Label r11;

    @FXML
    private Label r2;

    @FXML
    private Label r3;

    @FXML
    private Label r4;

    @FXML
    private Label r5;

    @FXML
    private Label r6;

    @FXML
    private Label r7;

    @FXML
    private Label r8;

    @FXML
    private Label r9;

    @FXML
    private Button schoolsButton;

    @FXML
    private ImageView t0;

    @FXML
    private ImageView t1;

    @FXML
    private ImageView t10;

    @FXML
    private ImageView t11;

    @FXML
    private ImageView t2;

    @FXML
    private ImageView t3;

    @FXML
    private ImageView t4;

    @FXML
    private ImageView t5;

    @FXML
    private ImageView t6;

    @FXML
    private ImageView t7;

    @FXML
    private ImageView t8;

    @FXML
    private ImageView t9;

    @FXML
    private Label y0;

    @FXML
    private Label y1;

    @FXML
    private Label y10;

    @FXML
    private Label y11;

    @FXML
    private Label y2;

    @FXML
    private Label y3;

    @FXML
    private Label y4;

    @FXML
    private Label y5;

    @FXML
    private Label y6;

    @FXML
    private Label y7;

    @FXML
    private Label y8;

    @FXML
    private Label y9;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setIslandsSceneController(this);
    }
    public void buildDataStructures(){
        Scene scene = b0.getScene();
        islands = new HashMap<>();

        Map<Color,Label> students;
        ImageView island;
        Circle circle;
        ImageView tower;
        ImageView motherNature;
        Label numTowers;

        for(int islandId = 0; islandId<12; islandId++){
            students = new HashMap<>();
            island = (ImageView)scene.lookup("#i"+islandId);
            circle = (Circle)scene.lookup("#c"+islandId);
            tower = (ImageView)scene.lookup("#t"+islandId);
            motherNature = (ImageView)scene.lookup("#m"+islandId);
            numTowers = (Label) scene.lookup("#numT"+islandId);
            for(Color color: Color.values()){
                students.put(color, (Label)scene.lookup("#"+color.getId()+islandId));
            }
            islands.put(islandId,new IslandGuiLogic(students,island,circle,tower,motherNature,numTowers));
        }
    }

    @FXML
    void switchToCards(ActionEvent event) {
        Eriantys.getCurrentApplication().switchToCardsScene();
    }

    @FXML
    void switchToSchools(ActionEvent event){
        Eriantys.getCurrentApplication().switchToSchools2PScene();
    }

    public Map<Integer, IslandGuiLogic> getIslands() {
        return islands;
    }
}
