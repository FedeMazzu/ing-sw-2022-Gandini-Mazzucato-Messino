package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.*;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class to create and manage the characters
 * @author filippogandini
 */
public class Shop implements Creator, Serializable {
    private Game game;
    private Random rand = new Random();
    private final int numOfCharacters= 12;
    private int[] randomCharacter;
    private List<Character> characters;

    public Shop(Game game){
        this.game = game;
        characters = new ArrayList<>();
        randomCharacter = new int[3];
        factoryMethod();
    }

    public void factoryMethod(){
        //getting 3 random integers
        for(int i=0; i<3; i++){randomCharacter[i] = rand.nextInt(numOfCharacters);}
        //naively
        while(randomCharacter[0] == randomCharacter[1]){
            randomCharacter[1] = rand.nextInt(numOfCharacters);
        }
        while(randomCharacter[2] == randomCharacter[1] || randomCharacter[2] == randomCharacter[0] ){
            randomCharacter[2] = rand.nextInt(numOfCharacters);
        }

        //creating the 3 random characters
        for(int i=0; i<3; i++){

            //because randInt returns a value between 0  and randomCharacter-1
            switch (randomCharacter[i]+1){
                case 1 :
                    characters.add(new Character1(game));
                    break;
                case 2:
                    characters.add(new Character2(game));
                    break;
                case 3:
                    characters.add(new Character3(game));
                    break;
                case 4:
                    characters.add(new Character4(game));
                    break;
                case 5:
                    characters.add(new Character5(game));
                    break;
                case 6:
                    characters.add(new Character6(game));
                    break;
                case 7:
                    characters.add(new Character7(game));
                    break;
                case 8:
                    characters.add(new Character8(game));
                    break;
                case 9:
                    characters.add(new Character9(game));
                    break;
                case 10:
                    characters.add(new Character10(game));
                    break;
                case 11:
                    characters.add(new Character11(game));
                    break;
                default://for safety
                    characters.add(new Character12(game));
                    break;
            }
        }


    }

    /**
     * @param arrayPosition
     * @return the random value extracted at the passed position
     */
    public int getRandomCharacterId(int arrayPosition){
        return randomCharacter[arrayPosition];
    }

    /**
     * @param listPosition
     * @return the Character instantiated  at the passed position in the list
     */
    public Character getCharacter(int listPosition){
        return characters.get(listPosition);
    }
}
