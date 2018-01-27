/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegametest;

import java.util.ArrayList;

public class Player {
    
    private final int STARTING_HAND = 7;
    
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> discard;
    private Deck deck;
    
    //Creating Player object.
    public Player(String name)
    {
        this.name = name;
        
        //Creates a new deck and shuffles it.
        deck = new Deck();
        deck.shuffle();
        
        //Draw cards to create beginning hand.
        for(int i = 0; i < STARTING_HAND; i++)
            hand.add(deck.draw());
        
        //Initialize discard.
        discard = new ArrayList(0);
        
    }
    
    //Player draws a card from their deck.
    public void draw()
    {
        hand.add(deck.draw());
    }
    
    //Player discards their hand.
    public void discard()
    {
        while(hand.size() > 0)
            discard.add(hand.remove(hand.size()-1));
    }
    
    //Returns card at specified index in hand.
    public Card getCardFromHand(int index)
    {
        return hand.get(index);
    }
    
    //Removed card at given index from hand.
    public Card removeCardFromHand(int index)
    {
        return hand.remove(index);
    }
    
    //Returns player hand.
    public ArrayList<Card> getHand()
    {
        return hand;
    }
    
    //Returns discard list.
    public ArrayList<Card> getDiscard()
    {
        return discard;
    }
    
}
