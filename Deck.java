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
                    case 3:
                        cardlist.add(new Card("Surge", 3, "Multiply the value of adjacent tiles by 2.",new Effect('*',2),'A'));
                        break;
                    case 4:
                        cardlist.add(new Card("Boost Signal", 4, "Increase the value of any tile",new Effect('+',2),'X'));
                        break;
                    case 5:
                        cardlist.add(new Card("Signal Tower", 5, "No effect.",new Effect('/',0),'/'));
                        break;
                    case 6:
                        cardlist.add(new Card("Dampen Signal", 6, "Reduce the value of any tile by 2.", new Effect('-',2),'X'));
                        break;
                    case 7:
                        cardlist.add(new Card("New Equipment",7,"If your hand size is 4 or higher, draw until you have 7 cards.",new Effect('D',0),'S'));
                        break;
                    case 8:
                        cardlist.add(new Card("Multi-Point Broadcast", 8, "Non-adjacent tiles are increased by 2.", new Effect('+',2), 'N'));
                        break;
                    case 9:
                        cardlist.add(new Card("Broad Spectrum Dampener", 9, "Reduce adjacent tiles by 2.", new Effect('-', 2), 'A'));
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
