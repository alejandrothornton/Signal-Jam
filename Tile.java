/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegametest;

import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class Tile {
    
    private boolean empty;
    private Card playedCard;
    private int ownedPlayer;
    private ArrayList<Effect> effects;
    
    private int currentValue;
    
    public Tile()
    {
        empty = true;
        ownedPlayer = -1;
    }
    
    //Placing a tile on the board
    public void placeTile(Card playedCard, int player)
    {
        this.playedCard = playedCard;
        ownedPlayer = player;
        empty = false;
    }
    
    //Adding an effect to a tile. Effects are added into the list while maintaining order of priority.
    public void addEffect(Effect eff)
    {
        boolean placed = false;
        int current = 0;
        int incomingType = eff.getPriority();
        
        if(effects.size() > 0)
            while(!placed && current != effects.size())
            {
                if(incomingType <= effects.get(current).getPriority())
                {
                    effects.add(current, eff);
                    placed = true;
                }
            }
        
        effects.add(eff);
        
    }
    
    //Clears the tile (usually after a round is over).
    //Right now doesn't actually clear data, just sets empty to true.
    public void clearTile()
    {
        empty = true;
        ownedPlayer = -1;
    }
    
    //Returns the value of boolean empty.
    public boolean isEmpty()
    {
        return empty;
    }
    
    public int getTileValue()
    {
        if(ownedPlayer == -1)
            return 0;
        
        int tileScore = 0;
        
        tileScore += playedCard.getValue();
        
        for(int i = 0; i < effects.size(); i++)
        {
            switch(effects.get(i).getType())
            {
                case '0':
                    tileScore = 0;
                    break;
                case '-':
                    tileScore = tileScore - effects.get(i).getModifier();
                    break;
                case '+':
                    tileScore = tileScore + effects.get(i).getModifier();
                    break;
                case '*':
                    tileScore = tileScore * effects.get(i).getModifier();
                    break;
            }
        }
        
        return tileScore;
    }
    
    //Update the current value.
    public void updateTileValue()
    {
        currentValue = getTileValue();
    }
    
    //BASIC GETTERS
    public Card getPlayedCard()
    {
        return playedCard;
    }
    
    public int getPlayer()
    {
        return ownedPlayer;
    }
    
}
