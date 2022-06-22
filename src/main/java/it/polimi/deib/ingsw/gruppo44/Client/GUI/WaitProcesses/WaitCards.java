package it.polimi.deib.ingsw.gruppo44.Client.GUI.WaitProcesses;

import it.polimi.deib.ingsw.gruppo44.Client.GUI.Eriantys;
import it.polimi.deib.ingsw.gruppo44.Client.GUI.MessagesMethodsGUI;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

/**
 * Runnable to wait the client turn and set up the Cards gui before the player can choose
 */
public class WaitCards implements Runnable{

    @Override
    public void run() {

        ObjectInputStream ois = Eriantys.getCurrentApplication().getOis();

        try {

            Eriantys.getCurrentApplication().getGameData().setCloudsData((CloudsData) ois.readObject());
            boolean gameSuspended = MessagesMethodsGUI.receiveSuspendedGameInfo();
            if(gameSuspended){
                Eriantys.getCurrentApplication().getSocket().close();
                Eriantys.getCurrentApplication().switchToStartingScene();
                return;
            }

            Map<Magician,Integer> playedCards =(Map<Magician, Integer>) ois.readObject();

            if(playedCards.isEmpty()){
                Eriantys.getCurrentApplication().getCardsSceneController().setFirstPlayer(true);
            }
            List<Integer> availableCards = Eriantys.getCurrentApplication().getGameData().getAvailableCards();


            Platform.runLater(()->{
                Eriantys.getCurrentApplication().getCardsSceneController().getSuspendButton().setVisible(playedCards.isEmpty());
                ImageView [] images = Eriantys.getCurrentApplication().getCardsSceneController().getImages();
                for(int i=1;i<=10;i++){
                    if(availableCards.contains(i)) {
                        images[i-1].setVisible(true);
                        images[i-1].setOpacity(1);
                    }
                    if(playedCards.values().contains(i)) images[i-1].setOpacity(0.25);
                }
            });

            Eriantys.getCurrentApplication().switchToCardsScene();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
