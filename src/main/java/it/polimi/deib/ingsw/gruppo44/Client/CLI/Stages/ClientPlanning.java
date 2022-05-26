package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
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
        boolean gameSuspension = ois.readBoolean();
        if(gameSuspension){
            //stop the game
            System.out.println("Another player suspended the game, the application will now close");
            clientController.getSocket().close();
            System.exit(0); //we kill the program and you have to open another one
        }
        //print the map of the cards played by others
        Map<Magician,Integer> playedCards = (Map<Magician,Integer>)(ois.readObject());
        if(playedCards.isEmpty()){
            System.out.println("Do you want to suspend the game here and continue?");
            System.out.println("1 -> YES\n 0 -> NO");
            boolean suspendGame = sc.nextInt() == 1;
            oos.writeBoolean(suspendGame);
            oos.flush();
            if(suspendGame){
                //stop the game
                System.out.println("The application will now close");
                clientController.getSocket().close();
                System.exit(0); //we kill the program
            }
        }
        System.out.println("Choose a card, any card:");
        System.out.println((playedCards));

        //printing available cards
        List<Integer> availableCards = clientController.getGameDataCLI().getAvailableCards();
        //it's not possible to play cards played from other players
        for(Integer i : playedCards.values()){
            if(availableCards.contains(i)) availableCards.remove(i);
        }
        System.out.println(availableCards);

        //sending the chosen card
        int cardChoice = sc.nextInt();
        clientController.setLastCardSelected(cardChoice);
        oos.writeInt(cardChoice);
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
