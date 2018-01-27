/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegametest;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Alejandro
 */

//Class for deck operations.
public class Deck {
    
    private ArrayList<Card> cardlist;
    
    //Default deck creation.
    public Deck()
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 3; i++)
            {
                switch(i)
                {
                    case 0:
                        cardlist.add(new Card("Jammer",0,"Reduce the base value of an opponent's tile to zero.",new Effect('0',0), 'O'));
                        break;
                    case 1:
                        cardlist.add(new Card("Back to the Drawing Board", 1, "You may discard your hand. If you do, draw 7 cards.",new Effect('H',0),'S'));
                        break;
                    case 2:
                        cardlist.add(new Card("Fortify Broadcast", 2, "Multiply the value of any tile by 2.", new Effect('*',2),'X'));
                        break;
                }
            }
        }
        
    }
    
    //Shuffling the deck.
    public void shuffle()
    {
        Collections.shuffle(cardlist);//Edited by Joseph uses collections shuffle, to shuffle deck
    }
    
    //Drawing a card from the deck.
    public Card draw()
    {
        return cardlist.remove(cardlist.size() - 1);
    }
}
