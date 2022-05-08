package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observable;
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
public class Shop implements Creator, Serializable, Observable {
    private Game game;
    private Random rand = new Random();
    private final int numOfCharacters= 12;
    private int[] randomCharacter;
    private List<Character> characters;
    private BoardObserver boardObserver;

    public Shop(Game game,BoardObserver boardObserver){
        this.game = game;
        this.boardObserver = boardObserver;
        characters = new ArrayList<>();
        randomCharacter = new int[3];
        factoryMethod();
    }

    @Override
    public void notifyObserver() {
        boardObserver.update();
    }

    public void factoryMethod(){
        //getting 3 random integers
        for(int i=0; i<3; i++){randomCharacter[i] = rand.nextInt(numOfCharacters);}
        //naively
        randomCharacter[0] = 3;
        while(randomCharacter[0] == randomCharacter[1]){
            randomCharacter[1] = rand.nextInt(numOfCharacters);
        }
        randomCharacter[1] = 11;
        while(randomCharacter[2] == randomCharacter[1] || randomCharacter[2] == randomCharacter[0] ){
            randomCharacter[2] = rand.nextInt(numOfCharacters);
        }


        //creating the 3 random characters
        for(int i=0; i<3; i++){

            //because randInt returns a value between 0 and randomCharacter-1
            switch (randomCharacter[i]+1){
                case 1 :
                    characters.add(new Character1(game,boardObserver));
                    break;
                case 2:
                    characters.add(new Character2(game,boardObserver));
                    break;
                case 3:
                    characters.add(new Character3(game,boardObserver));
                    break;
                case 4:
                    characters.add(new Character4(game,boardObserver));
                    break;
                case 5:
                    characters.add(new Character5(game,boardObserver));
                    break;
                case 6:
                    characters.add(new Character6(game,boardObserver));
                    break;
                case 7:
                    characters.add(new Character7(game,boardObserver));
                    break;
                case 8:
                    characters.add(new Character8(game,boardObserver));
                    break;
                case 9:
                    characters.add(new Character9(game,boardObserver));
                    break;
                case 10:
                    characters.add(new Character10(game,boardObserver));
                    break;
                case 11:
                    characters.add(new Character11(game,boardObserver));
                    break;
                default://for safety
                    characters.add(new Character12(game,boardObserver));
                    break;
            }

            //notifyObserver();
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
     * @return the list of characters in the actual game
     */
    public List<Character> getCharacters(){
        return characters;
    }

    public Character getSingleCharacter(int id){
        for(Character character:characters){
            if(character.getId() == id) return character;
        }
        return null;
    }
}
