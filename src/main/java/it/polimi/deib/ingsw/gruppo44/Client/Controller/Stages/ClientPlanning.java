package it.polimi.deib.ingsw.gruppo44.Client.Controller.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.Controller.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Controller.Ticket;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Card;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * class to manage  the Client Planning
 * @author filippogandini
 */
public class ClientPlanning implements Stage {
    private ClientController clientController;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Scanner sc = new Scanner(System.in);

    public ClientPlanning(ClientController clientController) {
        this.clientController = clientController;
        ois = clientController.getOis();
        oos = clientController.getOos();
    }
    @Override
    public void handle() throws IOException, ClassNotFoundException, InterruptedException {

        CloudsData cloudsData = (CloudsData)ois.readObject();
        System.out.println("waiting for your turn of choosing a card");
        //print the map of the cards played by others
        System.out.println((Map<Magician,Integer>)(ois.readObject()));

        //printing available cards
        System.out.println((ois.readObject()));

        //sending the chosen card
        oos.writeInt(sc.nextInt());
        oos.flush();

        System.out.println("Card played! waiting for others..");

        //receiving the turnNumber of this round
        int turnNumber = ois.readInt();
        clientController.setTurnNumber(turnNumber);
        System.out.println("Your turn number is "+turnNumber);

        if(turnNumber == 0) clientController.setClientStage(ClientStage.ClientACTION);
        else clientController.setClientStage(ClientStage.WaitingBeforeTurn);
    }
}
