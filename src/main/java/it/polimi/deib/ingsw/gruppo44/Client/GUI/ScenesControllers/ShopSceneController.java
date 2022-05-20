package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.CharacterGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.Logic.IslandGuiLogic;
import it.polimi.deib.ingsw.gruppo44.Client.MessagesMethods;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

public class ShopSceneController implements Initializable {

    private List<CharacterGuiLogic> characters;

    @FXML
    private Label id1,id2,id3;
    @FXML
    private Button chooseColorButton;

    @FXML
    private ListView<Color> colorLV;

    @FXML
    private Button cardsbutton,notBuyButton;

    @FXML
    private ImageView character1;

    @FXML
    private Label character1Label;

    @FXML
    private ImageView character2;

    @FXML
    private Label character2Label;

    @FXML
    private ImageView character3;

    @FXML
    private Label character3Label;

    @FXML
    private Button islandsbutton;

    @FXML
    private Rectangle rect1;

    @FXML
    private Rectangle rect2;

    @FXML
    private Rectangle rect3;

    @FXML
    private Button schoolsbutton;

    @FXML
    void switchToCards(ActionEvent event) {

    }

    @FXML
    void switchToIslands(ActionEvent event) {
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }

    @FXML
    void switchToSchools(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setShopSceneController(this);
    }

    public void buildDataStructures(){
        Scene currScene = rect1.getScene();
        characters = new ArrayList<>();

        for(int i=0;i<3;i++){

            CharacterGuiLogic tempChar = new CharacterGuiLogic((ImageView) currScene.lookup("#character"+(i+1)),
                    (ImageView) currScene.lookup("#m"+(i+1)),
                    (Rectangle) currScene.lookup("#rect"+(i+1)),
                    (Label) currScene.lookup(("#id"+(i+1)))
            );
            characters.add(tempChar);
        }
    }

    public void setCharactersVisibility(boolean value){
        for(CharacterGuiLogic cgl : characters){
            cgl.getCoin().setVisible(value);
            cgl.getHighlight().setVisible(value);
            cgl.getImage().setVisible(value);
            cgl.getIdLabel().setVisible(value);
        }
    }

    public CharacterGuiLogic cglFromId(int id){
        CharacterGuiLogic ans = null;
        for(CharacterGuiLogic cgl:characters){
            if(cgl.getId() == id) ans = cgl;
        }
        return ans;
    }

    public List<CharacterGuiLogic> getCharacters() {
        return characters;
    }

    public void doNotBuy(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeBoolean(false);
        oos.flush();
        notBuyButton.setVisible(false);
        for(CharacterGuiLogic cgl: characters){
            cgl.getHighlight().setVisible(false);
        }
        MessagesMethods.setForMovingStudents();
    }

    public Button getNotBuyButton() {
        return notBuyButton;
    }

    public void effectImage1(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(0).getId());
    }
    public void effectImage2(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(1).getId());

    }
    public void effectImage3(MouseEvent mouseEvent) throws IOException {
        sendCharacter(characters.get(2).getId());

    }

    private void sendCharacter(int id) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();

        //sending the server the decision of using a character
        oos.writeBoolean(true);
        oos.flush();

        //sending the character id used
        oos.writeInt(id);
        oos.flush();

        notBuyButton.setVisible(false);
        for(CharacterGuiLogic cgl: characters) cgl.getHighlight().setVisible(false);
        Eriantys.getCurrentApplication().getIslandsSceneController().writeInInfo("Character bought!");
        new Thread(()->{
            try {
                handleCharacter(id);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();

    }





    private void handleCharacter(int currentCharacter) throws IOException, ClassNotFoundException {
        boolean endGame = false;
        switch (currentCharacter){
            case 1:
                break;
            case 2:
                handleCharacter2();
                break;
            case 3:
                endGame = handleCharacter3();
                break;
            case 4:
                handleCharacter4();
                break;
            case 6:
                handleCharacter6();
                break;
            case 8:
                handleCharacter8();
                break;
            case 9:
                handleCharacter9();
                break;
                case 10:
                handleCharacter10();
                break;
            case 12:
                handleCharacter12();
                break;
        }
        if(!endGame) {
            //receiving the updated money after using a character
            MessagesMethods.receiveSchoolsUpdated();
            //receiving the updated prices
            MessagesMethods.receiveUpdatedPrices();

            //to play the standard turn
            MessagesMethods.setForMovingStudents();
        }
    }

    private void handleCharacter2() throws IOException, ClassNotFoundException {
        //managed server side
    }
    private void handleCharacter4() {

    }

    private boolean handleCharacter3() throws IOException, ClassNotFoundException {
        Platform.runLater(()->{
            Eriantys.getCurrentApplication().getIslandsSceneController().setUsingCharacter3True();
            for(IslandGuiLogic igl:Eriantys.getCurrentApplication().getIslandsSceneController().getIslands().values()){
                if(!igl.isCovered()) {
                    igl.getCircle().setVisible(true);
                }
            }
            Eriantys.getCurrentApplication().switchToIslandsScene();

        });

        MessagesMethods.receiveIslandsUpdated();

         boolean endGame = Eriantys.getCurrentApplication().getOis().readBoolean();
         if(endGame){
             Eriantys.getCurrentApplication().switchToEndGameScene();
         }
         //to not move to the standard turn
         return endGame;
    }

    private void handleCharacter6(){
        //managed server side
    }

    private void handleCharacter8(){
        //managed server side
    }

    private void handleCharacter9()  {
        setCharactersVisibility(false);
        for(Color c: Color.values()){
            colorLV.getItems().add(c);
        }
        colorLV.setVisible(true);
        chooseColorButton.setVisible(true);

    }

    public void chooseColor(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        Color color = (Color) colorLV.getSelectionModel().getSelectedItem();
        if(color!=null){
            oos.writeObject(color);
            oos.flush();
        }
        chooseColorButton.setVisible(false);
        colorLV.getItems().clear();
        colorLV.setVisible(false);
        setCharactersVisibility(true);
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }


    private void handleCharacter10() throws IOException, ClassNotFoundException {
       /* System.out.println("You want to swap 2 students in your school?\n" +
                "0 -> NO\n 1 -> YES");
        int swapChoice = sc.nextInt();
        if(swapChoice == 0){
            oos.writeBoolean(false);
            oos.flush();
            return;
        }
        else{
            oos.writeBoolean(true);
            oos.flush();
            System.out.println("Which 2 colors to swap? (First Hall then Entrance)");
            System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
            int [] colorChoices = new int[2];
            colorChoices[0] = sc.nextInt(); //Hall Student Color
            colorChoices[1] = sc.nextInt(); //Entrance Student Color
            for(int i=0;i<2;i++){
                switch (colorChoices[i]){
                    case 1:
                        oos.writeObject(Color.GREEN);
                        oos.flush();
                        break;
                    case 2:
                        oos.writeObject(Color.RED);
                        oos.flush();
                        break;
                    case 3:
                        oos.writeObject(Color.YELLOW);
                        oos.flush();
                        break;
                    case 4:
                        oos.writeObject(Color.PINK);
                        oos.flush();
                        break;
                    case 5:
                        oos.writeObject(Color.BLUE);
                        oos.flush();
                        break;
                    default:
                        System.out.println("incorrect value");
                        System.exit(0);
                        break;
                }
            }
            System.out.println("You want to swap 2 students in your school?\n" +
                    "0 -> NO\n 1 -> YES");
            swapChoice = sc.nextInt();
            if(swapChoice == 0){
                oos.writeBoolean(false);
                oos.flush();
                return;
            }
            else{
                oos.writeBoolean(true);
                oos.flush();
                System.out.println("Which 2 colors to swap? (First Hall then Entrance)");
                System.out.println(" 1-GREEN \n 2-RED \n 3-YELLOW \n 4-PINK \n 5-BLUE");
                colorChoices[0] = sc.nextInt(); //Hall Student Color
                colorChoices[1] = sc.nextInt(); //Entrance Student Color
                for(int i=0;i<2;i++){
                    switch (colorChoices[i]){
                        case 1:
                            oos.writeObject(Color.GREEN);
                            oos.flush();
                            break;
                        case 2:
                            oos.writeObject(Color.RED);
                            oos.flush();
                            break;
                        case 3:
                            oos.writeObject(Color.YELLOW);
                            oos.flush();
                            break;
                        case 4:
                            oos.writeObject(Color.PINK);
                            oos.flush();
                            break;
                        case 5:
                            oos.writeObject(Color.BLUE);
                            oos.flush();
                            break;
                        default:
                            System.out.println("incorrect value");
                            System.exit(0);
                            break;
                    }
                }

            }

        }
        MessagesMethods.receiveSchoolsUpdated();

        */
    }

    private void handleCharacter12() throws IOException, ClassNotFoundException {
        setCharactersVisibility(false);
        for(Color c: Color.values()){
            colorLV.getItems().add(c);
        }
        colorLV.setVisible(true);
        chooseColorButton.setVisible(true);

    }

}
