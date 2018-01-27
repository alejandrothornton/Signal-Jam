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
public class Effect {
    
    /* 
        Type of Effect, legend below: 
        
        '0': Zero
        '-': Subtract
        '+': Add
        '*': Multiply
        'H': Hand
        'D': Draw
        '/': None
    */
    private char type;
    private int modifier;
    
    public Effect(char type, int mod)
    {
        this.type = type;
        this.modifier = mod;
    }
    
    //Get priority value for effect type. 
    //Note that Hand and Draw do not have a priority as they are not used in score calculation.
    public int getPriority()
    {
        switch(type)
        {
            case '0':
                return 1;
            case '-':
                return 2;
            case '+':
                return 3;
            case '*':
                return 4;
        }
        
        //Error
        return -1;
    }
    
    //BASIC GETTERS
    public char getType()
    {
        return type;
    }
    
    public int getModifier()
    {
        return modifier;
    }
    
}
