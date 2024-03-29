package it.polimi.deib.ingsw.gruppo44.Client.GUI.ScenesControllers;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.WaitProcesses.Wait;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the cards scene
 */
public class CardsSceneController implements Initializable {
    @FXML
    private ImageView im1,im2,im3,im4,im5,im6,im7,im8,im9,im10;
    @FXML
    private Button schoolsButton,islandsButton, suspendButton;

    private int lastCardSel;

    private ImageView[]images;
    private ObjectOutputStream oos;
    private boolean firstPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eriantys.getCurrentApplication().setCardsSceneController(this);
        oos = Eriantys.getCurrentApplication().getOos();
        images = new ImageView[]{im1,im2,im3,im4,im5,im6,im7,im8,im9,im10};
        for(int i=0;i<10;i++) images[i].setVisible(false);
        firstPlayer = false;
    }

    @FXML
    void ChooseIm1(MouseEvent event) throws IOException {
        if(im1.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(1);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==1) im1.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 1;
        new Thread(new Wait()).start();

    }

    @FXML
    void ChooseIm2(MouseEvent event) throws IOException {
        if(im2.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(2);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==2) im2.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 2;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm3(MouseEvent event) throws IOException {
        if(im3.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(3);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==3) im3.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 3;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm4(MouseEvent event) throws IOException {
        if(im4.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(4);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==4) im4.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 4;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm5(MouseEvent event) throws IOException {
        if(im5.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(5);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==5) im5.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 5;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm6(MouseEvent event) throws IOException {
        if(im6.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(6);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==6) im6.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 6;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm7(MouseEvent event) throws IOException {
        if(im7.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(7);
        oos.flush();

        for(int i=1;i<=10;i++){
            if(i==7) im7.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 7;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm8(MouseEvent event) throws IOException {
        if(im8.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(8);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==8) im8.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 8;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm9(MouseEvent event) throws IOException {
        if(im9.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(9);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==9) im9.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 9;
        new Thread(new Wait()).start();
    }

    @FXML
    void ChooseIm10(MouseEvent event) throws IOException {
        if(im10.getOpacity()<1.0) return;
        sendSuspension(oos);
        oos.writeInt(10);
        oos.flush();
        for(int i=1;i<=10;i++){
            if(i==10) im10.setOpacity(0.98);
            else images[i-1].setOpacity(0.25);
        }
        lastCardSel = 10;
        new Thread(new Wait()).start();
    }
    @FXML
    public void suspendGame(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos = Eriantys.getCurrentApplication().getOos();
        oos.writeBoolean(true);
        oos.flush();
        new Thread(()->{
            try {
                Eriantys.getCurrentApplication().getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Eriantys.getCurrentApplication().switchToStartingScene();

        }).start();
    }

    private void sendSuspension(ObjectOutputStream oos) throws IOException {
        if(firstPlayer){
            oos.writeBoolean(false);
            oos.flush();
            concealSuspendButton();
            firstPlayer = false;
        }
    }

    private void concealSuspendButton(){
        suspendButton.setVisible(false);
    }

    public int getLastCardSel() {
        return lastCardSel;
    }

    public ImageView[] getImages() {return images;}

    public Button getSuspendButton(){return suspendButton;}

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void switchToSchools(ActionEvent actionEvent) throws IOException {
        Eriantys.getCurrentApplication().switchToSchools2PScene();
    }



    public void switchToIslands(ActionEvent actionEvent) {
        Eriantys.getCurrentApplication().switchToIslandsScene();
    }
}
