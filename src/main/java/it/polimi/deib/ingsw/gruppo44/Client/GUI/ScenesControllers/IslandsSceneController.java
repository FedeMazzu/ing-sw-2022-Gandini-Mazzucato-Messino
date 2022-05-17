package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;


import it.polimi.deib.ingsw.gruppo44.Client.Controller.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CloudGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * class to manage the islands GUI
 */
public class IslandsSceneController implements Initializable {

    private Map<Integer, IslandGuiLogic> islands;
    private Map<Integer,CloudGuiLogic> clouds;

    private int phase; //-1 Nothing, 0 student selection, 1 mother nature move, 2 cloud choice
    private int counter; //num of student the player has already moved

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
    private ImageView bI0;

    @FXML
    private ImageView bI1;

    @FXML
    private ImageView bI10;

    @FXML
    private ImageView bI11;

    @FXML
    private ImageView bI2;

    @FXML
    private ImageView bI3;

    @FXML
    private ImageView bI4;

    @FXML
    private ImageView bI5;

    @FXML
    private ImageView bI6;

    @FXML
    private ImageView bI7;

    @FXML
    private ImageView bI8;

    @FXML
    private ImageView bI9;

    @FXML
    private ImageView bIcloud0;

    @FXML
    private ImageView bIcloud1;

    @FXML
    private ImageView bIcloud2;

    @FXML
    private ImageView bIcloud3;

    @FXML
    private Label bcloud0;

    @FXML
    private Label bcloud1;

    @FXML
    private Label bcloud2;

    @FXML
    private Label bcloud3;

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
    private Circle ccloud0;

    @FXML
    private Circle ccloud1;

    @FXML
    private Circle ccloud2;

    @FXML
    private Circle ccloud3;

    @FXML
    private ImageView cloud0;

    @FXML
    private ImageView cloud1;

    @FXML
    private ImageView cloud2;

    @FXML
    private ImageView cloud3;

    @FXML
    private ListView<Color> entranceStudentsSelection;

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
    private ImageView gI0;

    @FXML
    private ImageView gI1;

    @FXML
    private ImageView gI10;

    @FXML
    private ImageView gI11;

    @FXML
    private ImageView gI2;

    @FXML
    private ImageView gI3;

    @FXML
    private ImageView gI4;

    @FXML
    private ImageView gI5;

    @FXML
    private ImageView gI6;

    @FXML
    private ImageView gI7;

    @FXML
    private ImageView gI8;

    @FXML
    private ImageView gI9;

    @FXML
    private ImageView gIcloud0;

    @FXML
    private ImageView gIcloud1;

    @FXML
    private ImageView gIcloud2;

    @FXML
    private ImageView gIcloud3;

    @FXML
    private Label gcloud0;

    @FXML
    private Label gcloud1;

    @FXML
    private Label gcloud2;

    @FXML
    private Label gcloud3;

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
    private ImageView pI0;

    @FXML
    private ImageView pI1;

    @FXML
    private ImageView pI10;

    @FXML
    private ImageView pI11;

    @FXML
    private ImageView pI2;

    @FXML
    private ImageView pI3;

    @FXML
    private ImageView pI4;

    @FXML
    private ImageView pI5;

    @FXML
    private ImageView pI6;

    @FXML
    private ImageView pI7;

    @FXML
    private ImageView pI8;

    @FXML
    private ImageView pI9;

    @FXML
    private ImageView pIcloud0;

    @FXML
    private ImageView pIcloud1;

    @FXML
    private ImageView pIcloud2;

    @FXML
    private ImageView pIcloud3;

    @FXML
    private Label pcloud0;

    @FXML
    private Label pcloud1;

    @FXML
    private Label pcloud2;

    @FXML
    private Label pcloud3;

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
    private ImageView rI0;

    @FXML
    private ImageView rI1;

    @FXML
    private ImageView rI10;

    @FXML
    private ImageView rI11;

    @FXML
    private ImageView rI2;

    @FXML
    private ImageView rI3;

    @FXML
    private ImageView rI4;

    @FXML
    private ImageView rI5;

    @FXML
    private ImageView rI7;

    @FXML
    private ImageView rI8;

    @FXML
    private ImageView rI9;

    @FXML
    private ImageView rIcloud0;

    @FXML
    private ImageView rIcloud1;

    @FXML
    private ImageView rIcloud2;

    @FXML
    private ImageView rIcloud3;

    @FXML
    private Label rcloud0;

    @FXML
    private Label rcloud1;

    @FXML
    private Label rcloud2;

    @FXML
    private Label rcloud3;

    @FXML
    private Button schoolSelectionButton;

    @FXML
    private Button schoolsButton;

    @FXML
    private Rectangle studentChoicePanel;

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

    @FXML
    private ImageView yI0;

    @FXML
    private ImageView yI1;

    @FXML
    private ImageView yI10;

    @FXML
    private ImageView yI11;

    @FXML
    private ImageView yI2;

    @FXML
    private ImageView yI3;

    @FXML
    private ImageView yI4;

    @FXML
    private ImageView yI5;

    @FXML
    private ImageView yI6;

    @FXML
    private ImageView yI7;

    @FXML
    private ImageView yI8;

    @FXML
    private ImageView yI9;

    @FXML
    private ImageView yIcloud0;

    @FXML
    private ImageView yIcloud1;

    @FXML
    private ImageView yIcloud2;

    @FXML
    private ImageView yIcloud3;

    @FXML
    private Label ycloud0;

    @FXML
    private Label ycloud1;

    @FXML
    private Label ycloud2;

    @FXML
    private Label ycloud3;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setIslandsSceneController(this);
    }
    public void buildDataStructures(){
        Scene scene = b0.getScene();
        islands = new HashMap<>();

        Map<Color,Label> students;
        Map<Color,ImageView> studentsSymbols;
        ImageView island;
        Circle circle;
        ImageView tower;
        ImageView motherNature;
        Label numTowers;
        this.counter = 0;
        this.phase = -1;
        ImageView studentSym;
        //islands
        for(int islandId = 0; islandId<12; islandId++){
            students = new HashMap<>();
            studentsSymbols = new HashMap<>();
            island = (ImageView)scene.lookup("#i"+islandId);
            circle = (Circle)scene.lookup("#c"+islandId);
            tower = (ImageView)scene.lookup("#t"+islandId);
            motherNature = (ImageView)scene.lookup("#m"+islandId);
            numTowers = (Label) scene.lookup("#numT"+islandId);

            circle.setVisible(false);
            for(Color color: Color.values()){
                students.put(color, (Label)scene.lookup("#"+color.getId()+islandId));
                studentSym =(ImageView) scene.lookup("#"+color.getId()+"I"+islandId);
                //need to understand which is the pattern for initializing the images automatically
                //studentSym.setImage(new  Image(getClass().getResource("/images/pawns/"+color.getId()+"s.png")));
                studentsSymbols.put(color, studentSym);
            }
            islands.put(islandId,new IslandGuiLogic(students,studentsSymbols,island,circle,tower,motherNature,numTowers));
        }

        //clouds
        clouds = new HashMap<>();
        for(int cloudId=0; cloudId<Eriantys.getCurrentApplication().getGameMode().getCloudsNumber(); cloudId++){
            students = new HashMap<>();
            studentsSymbols = new HashMap<>();
            ImageView cloud = (ImageView) scene.lookup("#cloud"+cloudId);
            cloud.setVisible(true);
            Circle circle1 = (Circle) scene.lookup("#ccloud"+cloudId);
            for(Color color: Color.values()){
                Label studentLabel = (Label)scene.lookup("#"+color.getId()+"cloud"+cloudId);
                studentLabel.setVisible(true);
                students.put(color, studentLabel);

                ImageView studentsSymbol = (ImageView) scene.lookup("#"+color.getId()+"Icloud"+cloudId);
                studentsSymbol.setVisible(true);
                studentsSymbols.put(color, studentsSymbol);
            }
            clouds.put(cloudId,new CloudGuiLogic(students,studentsSymbols,cloud,circle1));
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

    public Map<Integer, CloudGuiLogic> getClouds() {
        return clouds;
    }

    public ListView<Color> getEntranceStudentsSelection(){
        return entranceStudentsSelection;
    }

    public Button getSchoolSelectionButton(){
        return schoolSelectionButton;
    }

    public Rectangle getStudentChoicePanel(){
        return studentChoicePanel;
    }

    public void selectSchool(ActionEvent actionEvent) throws IOException {
        moveEntranceStudent(-1);

    }
    public void selectIsland0(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(0);
        else if(phase == 1 && c0.isVisible()) moveMotherNature(0);

    }

    public void selectIsland1(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(1);
        else if(phase == 1 && c1.isVisible()) moveMotherNature(1);
    }

    public void selectIsland2(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(2);
        else if(phase == 1 && c2.isVisible()) moveMotherNature(2);
    }

    public void selectIsland3(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(3);
        else if(phase == 1 && c3.isVisible()) moveMotherNature(3);
    }

    public void selectIsland4(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(4);
        else if(phase == 1 && c4.isVisible()) moveMotherNature(4);
    }

    public void selectIsland5(MouseEvent mouseEvent) throws IOException {
        if(phase == 0 ) moveEntranceStudent(5);
        else if(phase == 1 && c5.isVisible()) moveMotherNature(5);
    }

    public void selectIsland6(MouseEvent mouseEvent) throws IOException {
        if(phase == 0 ) moveEntranceStudent(6);
        else if(phase == 1 && c6.isVisible()) moveMotherNature(6);
    }

    public void selectIsland7(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(7);
        else if(phase == 1 && c7.isVisible()) moveMotherNature(7);
    }

    public void selectIsland8(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(8);
        else if(phase == 1 && c8.isVisible()) moveMotherNature(8);
    }

    public void selectIsland9(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(9);
        else if(phase == 1 && c9.isVisible()) moveMotherNature(9);
    }

    public void selectIsland10(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(10);
        else if(phase == 1 && c10.isVisible()) moveMotherNature(10);
    }

    public void selectIsland11(MouseEvent mouseEvent) throws IOException {
        if(phase == 0) moveEntranceStudent(11);
        else if(phase == 1 && c11.isVisible()) moveMotherNature(11);
    }


    private void moveEntranceStudent(int island) throws IOException {
        //island is the island id of the selected island, -1 if it is a school

        Map<Color,Integer> entry = new HashMap<>();
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        Color color = entranceStudentsSelection.getSelectionModel().getSelectedItem();
        if(color != null){
            entry.put(color,island);
            oos.writeObject(entry);
            oos.flush();
        }
        counter++;
        entranceStudentsSelection.getItems().remove(color);
        new Thread(()->{
            try{
                System.out.println("Prima scuole");
                MessagesMethods.receiveSchoolUpdated();
                MessagesMethods.receiveIslandsUpdated();
                System.out.println("dopo tutto");
            }
            catch (Exception e){}
        }).start();
        if(counter >= 3){
            phase = 1;
            counter = 0;
            entranceStudentsSelection.getItems().clear();
            //set the choice panel invisible
            entranceStudentsSelection.setVisible(false);
            studentChoicePanel.setVisible(false);
            schoolSelectionButton.setVisible(false);
            //make mother nature move
            setupForMotherNature();
        }
    }

    private void moveMotherNature(int islandTarget) throws IOException {
        int currPos = Eriantys.getCurrentApplication().getGameData().getMotherNaturePosition();
        int numOfIslands = Eriantys.getCurrentApplication().getGameData().getIslandsData().getNumOfIslands();
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        for(IslandGuiLogic igl :islands.values()){
            igl.getCircle().setVisible(false);
        }
        oos.writeInt((islandTarget-currPos)%numOfIslands);
        oos.flush();
        new Thread(()->{
            try{
                MessagesMethods.receiveMotherNaturePos();
                boolean endGame = Eriantys.getCurrentApplication().getOis().readBoolean();
                if (endGame){
                    String winningMagician =(String) Eriantys.getCurrentApplication().getOis().readObject();

                    Platform.runLater(()->{
                        Eriantys.getCurrentApplication().getEndGameSceneController().getWinLabel().setText(winningMagician);
                    });

                    Eriantys.getCurrentApplication().switchToEndGameScene();
                }
            }
            catch (Exception e){}

        }).start();
        phase = 2; //go in cloud choice phase
        CloudGuiLogic cgl;
        Circle cloudCircle;
        for(int i=0; i<Eriantys.getCurrentApplication().getGameMode().getCloudsNumber(); i++){
            if(!Eriantys.getCurrentApplication().getGameData().getCloudsData().isEmpty(i)){
                cgl = clouds.get(i);
                cloudCircle = cgl.getCircle();
                cloudCircle.setVisible(true);
            }

        }
    }


    private void setupForMotherNature(){
        int currPos = Eriantys.getCurrentApplication().getGameData().getMotherNaturePosition();
        int currCard = Eriantys.getCurrentApplication().getCardsSceneController().getLastCardSel();
        int numOfMoves = (int)Math.ceil(((double)currCard/2.0));


        Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().get(currPos).getCircle().setVisible(false);

        for(int i=0,j=0;i<12;i++,j++){
            IslandGuiLogic igl = Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().get(i);
            if(igl.isCovered()){
                j--;
                continue;
            }
            if(((j-currPos)>0)&& (numOfMoves < (j-currPos))) igl.getCircle().setVisible(false);
        }


    }


    public void selectCloud0(MouseEvent mouseEvent) throws IOException {
        if(phase == 2 && ccloud0.isVisible()) chooseCloud(0);
    }
    public void selectCloud1(MouseEvent mouseEvent) throws IOException {
        if(phase == 2 && ccloud1.isVisible()) chooseCloud(1);
    }
    public void selectCloud2(MouseEvent mouseEvent) throws IOException {
        if(phase == 2 && ccloud2.isVisible()) chooseCloud(2);
    }
    public void selectCloud3(MouseEvent mouseEvent) throws IOException {
        if(phase == 2 && ccloud3.isVisible()) chooseCloud(3);
    }

    public void chooseCloud( int cloudId) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeInt(cloudId);
        oos.flush();
        for(CloudGuiLogic cgl: clouds.values()) cgl.getCircle().setVisible(false);
        phase =-1;

        new Thread(()->{
            try {
                MessagesMethods.receiveCloudsUpdated();
                MessagesMethods.receiveSchoolUpdated();
                synchronized (Eriantys.getCurrentApplication().getIslandsSceneController()){
                    Eriantys.getCurrentApplication().getIslandsSceneController().notifyAll(); //to wake up and go in wait after
                }
            } catch (IOException | ClassNotFoundException e) {

            }
        }).start();
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }
}
