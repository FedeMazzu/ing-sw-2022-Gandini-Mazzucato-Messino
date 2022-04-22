package it.polimi.deib.ingsw.gruppo44.Server.Controller;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Game;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Player;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Planning implements Stage {
    private final GameStage gameStage = GameStage.PLANNING;
    private final GameController gameController;
    private ArrayDeque<Player> cardOrder;
    private PriorityQueue<Ticket> turnOrder;
    private Scanner sc = new Scanner(System.in);

    public Planning(GameController gameController) {
        this.gameController = gameController;
        this.cardOrder = gameController.getTurnHandler().getCardOrder();
        this.turnOrder = gameController.getTurnHandler().getTurnOrder();
    }

    @Override
    public void handle() {
        System.out.println("-----------PLANNING PHASE----------------");
        while(!cardOrder.isEmpty()){
            Player currPlayer = cardOrder.poll();
            System.out.println("Player "+currPlayer.getMagician()+" choose a card: ");
            int cardValue = sc.nextInt();
            currPlayer.playCard(cardValue);
            turnOrder.add(new Ticket(currPlayer,cardValue));
        }
        gameController.setGameStage(GameStage.ACTION);
    }
}
