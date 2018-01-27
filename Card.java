/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegametest;

/**
 *
 * @author Alejandro
 */

//Class for the cards played in the game.
public class Card {
    
    //Card properties
    private String name;
    private int value;
    private String effectText;
    private Effect effect;
    
    
    /*  
        Target of Effect, legend below:
    
        'A': Adjacent
        'N': Non-adjacent
        'X': Any
        'O': Opponent
        'S': Self
        '/': None
    */
    private char effectTarget;
    
    public Card(String name, int value, String effectText, Effect effect, char effectTarget)
    {
        this.name = name;
        this.value = value;
        this.effectText = effectText;
        this.effect = effect;
        this.effectTarget = effectTarget;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public String getEffectText()
    {
        return effectText;
    }
    
    public Effect getEffect()
    {
        return effect;
    }
    
    public char getEffectType()
    {
        return effect.getType();
    }
    
    public int getEffectMod()
    {
        return effect.getModifier();
    }
    
    public char getEffectTarget()
    {
        return effectTarget;
    }
}
