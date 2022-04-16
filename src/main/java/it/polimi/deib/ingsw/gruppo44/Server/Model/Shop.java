package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Characters.Character1;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class to create and manage the characters
 * @author filippogandini
 */
public class Shop implements Creator{
    private Game game;
    private Random rand = new Random();
    private final int numOfCharacters= 12;
    private int[] randomCharacter;
    private List<Character> characters;

    public Shop(Game game){
        this.game = game;
        characters = new ArrayList<>();
        randomCharacter = new int[12];
        factoryMethod();
    }

    public void factoryMethod(){
        //getting 3 random integers
        for(int character : randomCharacter) character = rand.nextInt(numOfCharacters);;
        //naively
        while(randomCharacter[2] == randomCharacter[1]){
            randomCharacter[2] = rand.nextInt(numOfCharacters);
        }
        while(randomCharacter[3] == randomCharacter[2] || randomCharacter[3] == randomCharacter[1] ){
            randomCharacter[3] = rand.nextInt(numOfCharacters);
        }

        //creating the 3 random characters
        for(int character : randomCharacter){
            //because randInt returns a value between 0  and randomCharacter-1
            switch (character+1){
                case 1 :
                    characters.add(new Character1(game));
                    break;
                case 2: ;
                //.. to complete with all the characters

                default: ;

            }
        }


    }
}
