package it.polimi.deib.ingsw.gruppo44.Client.CLI.Stages;

import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientController;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.ClientStage;
import it.polimi.deib.ingsw.gruppo44.Client.CLI.MessagesMethodsCLI;
import it.polimi.deib.ingsw.gruppo44.Common.Stage;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Magician;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * class to manage  the Client Planning
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
        MessagesMethodsCLI.receiveCloudsUpdated();
        //CloudsData cloudsData = (CloudsData)ois.readObject();
        System.out.println("Waiting for your turn of choosing a card");
        boolean gameSuspension = ois.readBoolean();
        if(gameSuspension){
            //stop the game
            System.out.println("Another player suspended the game, the application will now close");
            clientController.getSocket().close();
            System.exit(0); //we kill the program and you have to open another one
        }

        Map<Magician,Integer> playedCards = (Map<Magician,Integer>)(ois.readObject());
        if(playedCards.isEmpty()){
            System.out.println("Do you want to suspend the game here?");
            System.out.println("0 -> NO\n1 -> YES");
            boolean suspendGame = sc.nextInt() == 1;
            oos.writeBoolean(suspendGame);
            oos.flush();
            if(suspendGame){
                //stop the game
                System.out.println("The application will now close");
                clientController.getSocket().close();
                System.exit(0); //we kill the program
            }
            System.out.println("Game continues..");
        }
        System.out.println("------------------------------------------------------------------------------------------\n" +
                "------------------------------------------------------------------------------------------\n" +
                "PLANNING PHASE\n" +
                "--------------------------");
        System.out.println("Cards played from the other players");
        System.out.println((playedCards));

        List<Integer> availableCards = clientController.getGameDataCLI().getAvailableCards();
        //it's not possible to play cards played from other players
        for(Integer i : playedCards.values()){
            if(availableCards.contains(i)) availableCards.remove(i);
        }

        //sending the chosen card
        int cardChoice;
        do {
            System.out.println("Choose a card, any number in:");
            System.out.println(availableCards);
            cardChoice = sc.nextInt();
        }while(!(availableCards.contains(cardChoice)));
        //sending the chosen card
        clientController.setLastCardSelected(cardChoice);
        oos.writeInt(cardChoice);
        oos.flush();

        System.out.println("Card played! waiting for others..");

        //receiving the turnNumber of this round
        int turnNumber = ois.readInt();
        clientController.setTurnNumber(turnNumber);
        System.out.println("Your are the "+(turnNumber+1)+"° player to play!");

        if(turnNumber == 0) clientController.setClientStage(ClientStage.ClientACTION);
        else clientController.setClientStage(ClientStage.WaitingBeforeTurn);
    }
}
