package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Common.GameMode;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test the Shop
 * @author filippogandini
 */
class ShopTest {
    private Game game;
    private Shop shop;
    private BoardObserver boardObserver;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * tests if the instantiated character corresponds to the random value extracted
     * Note that the test varies depending on the random extracted values
     */
    @Test
    public void factoryMethod__wocd(){
        game = new Game(GameMode.TwoPlayersExpert);
        shop = game.getBoard().getShop();
        assertEquals(shop.getRandomCharacterId(0)+1,shop.getCharacters().get(0).getId());
        assertEquals(shop.getRandomCharacterId(1)+1,shop.getCharacters().get(1).getId());
        assertEquals(shop.getRandomCharacterId(2)+1,shop.getCharacters().get(2).getId());

    }

   /*
     * tests the istantiation of all the characters.
     * Note that there are no asserts, it's just to check if they can be created without errors

    @Test
    public void CharactersIstantiations__woct(){
        for(int i=0; i<12; i++){
            //because randInt returns a value between 0  and randomCharacter-1
            switch (i+1){
                case 1 :
                    new Character1(game,);
                    break;
                case 2:
                    new Character2(game);
                    break;
                case 3:
                    new Character3(game);
                    break;
                case 4:
                    new Character4(game);
                    break;
                case 5:
                    new Character5(game);
                    break;
                case 6:
                    new Character6(game);
                    break;
                case 7:
                    new Character7(game);
                    break;
                case 8:
                    new Character8(game);
                    break;
                case 9:
                    new Character9(game);
                    break;
                case 10:
                    new Character10(game);
                    break;
                case 11:
                    new Character11(game);
                    break;
                default://for safety
                    new Character12(game);
                    break;
            }
        }

    }*/
}