package it.polimi.deib.ingsw.gruppo44.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test the Player class
 * @author filippogandini
 */

class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Filippo",Magician.MONK,GameMode.TwoPlayersBasic);
        for(Card card : Card.values()) card.setPlayedFalse();
    }

    @AfterEach
    public void tearDown() {
        player = null;
    }

    @Test
    public void addCoin__woct(){
        player.addCoin();
        player.addCoin();
        assertEquals(2,player.getMoney());
        assertNotEquals(3,player.getMoney());
        assertNotEquals(1,player.getMoney());
    }
    @Test
    public void addCoin__nwoud(){
        player.addCoin();
        assertNotEquals(2,player.getMoney());
        assertNotEquals(0,player.getMoney());

    }

    @Test
    public void playCard__woct(){
        for(int i=1; i<=10; i++) { //the number of cards is fixed
            assertFalse(player.getCard(i).isPlayed());
        }

        player.playCard(6);

        for(int i=1; i<=10; i++) { //the number of cards is fixed
            if(i == 6) assertTrue(player.getCard(i).isPlayed());
            else assertFalse(player.getCard(i).isPlayed());
        }

    }

    @Test
    public void showAvailableCards__woct(){
        List<Card> availableCards = new ArrayList<>();

        // using some cards
        player.playCard(3);
        player.playCard(7);
        availableCards = player.showAvailableCards();

        //checking if the played cards aren't available and if the others are
        for(int i=1; i<=10; i++){
            if(i==3 || i==7){
                assertFalse(availableCards.contains(player.getCard(i)));
            }else{
                assertTrue(availableCards.contains(player.getCard(i)));
            }
        }

    }
}