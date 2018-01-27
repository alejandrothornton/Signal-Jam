/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegametest;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alejandro
 */
public class GameSession {
    
    //Player objects
    private Player red;
    private Player blue;
    
    //Board object
    private Board gameBoard;
    
    //Number of rounds each player has won.
    private int redRounds;
    private int blueRounds;
    
    //Current player scores.
    private int currentRedScore;
    private int currentBlueScore;
    
    //The player whose turn it is: 0 is Red, 1 is Blue.
    private int playerTurn;
    
    //Sentinel booleans for match and round loops.
    boolean matchEnd;
    boolean roundEnd;
    
    //Booleans for determining the victor.
    boolean redWin;
    boolean blueWin;
    
    //Array for holding a player's move and line calculations.
    int[] move;
    int[] line;
    
    //Default constructor, variables have default values.
    public GameSession()
    {
        red = new Player("Red");
        blue = new Player("Blue");
        
        gameBoard = new Board();
        
        redRounds = 0;
        blueRounds = 0;
        
        currentRedScore = 0;
        currentBlueScore = 0;
        
        matchEnd = false;
        roundEnd = false;
        
        redWin = false;
        blueWin = false;
        
        move[0] = -1;
        move[1] = -1;
        
        line[0] = -1;
        line[1] = -1;
    }
    
    public void play()
    {
        //begin match loop
        while(!matchEnd)
        {
            //determine who goes first
            playerTurn = goesFirst();

            //begin round loop
            while(!roundEnd)
            {
                //player picks a card and a spot on the board for placement.
                if(playerTurn == 0) //red plays
                {
                    move = red.playCard();
                    //add card to tile
                    while(!gameBoard.placeTile(red.getCardFromHand(move[0]), playerTurn, move[1], move[2]))
                        move = red.playCard();
                    //resolve effects, if any
                    resolveEffect(red.removeCardFromHand(move[0]), move[1], move[2]);
                    //calculate current scores
                    calculateScores();
                    //check for line or full
                    if(roundEnd())
                        roundEnd = true;
                }
                else    //blue plays
                {
                    move = blue.playCard();
                    //add card to tile
                    while(!gameBoard.placeTile(blue.getCardFromHand(move[0]), playerTurn, move[1], move[2]))
                        move = blue.playCard();
                    //resolve effects, if any
                    resolveEffect(blue.removeCardFromHand(move[0]), move[1], move[2]);
                    //calculate current scores
                    calculateScores();
                    //check for line or full
                    if(roundEnd())
                        roundEnd = true;
                }
                
                //Clearing move array.
                move[0] = -1;
                move[1] = -1;
                move[2] = -1;
            }  //Loop ends if round is completed.
            
            //Compare scores, factor line bonus.
            if(line[0] > -1)
            {
                if(line[1] == 0)
                    currentRedScore += calculateLineBonus();
                else
                    currentBlueScore += calculateLineBonus();
            }
            
            //Increment relevant round counter.
            if(currentRedScore > currentBlueScore)
                redRounds++;
            else if(currentRedScore < currentBlueScore)
                blueRounds++;
            else
            {
                redRounds++;
                blueRounds++;
            }
            
            //Check for game over.
            if(redRounds == 2 && blueRounds < 2)
            {
                matchEnd = true;
                redWin = true; 
            }
            if(blueRounds == 2 && blueRounds < 2)
            {
                matchEnd = true;
                blueWin = true;
            }
            else if(redRounds == 2 && blueRounds == 2)
            {
                matchEnd = true;
            }
            
            //If the match is not over, reset variables for the next rounds.
            if(!matchEnd)
            {
                currentRedScore = 0;
                currentBlueScore = 0;
                
                line[0] = -1;
                line[1] = -1;
                
                move[0] = -1;
                move[1] = -1;
                move[2] = -1;

                gameBoard.clearBoard(red,blue);
            }
        }
        //Loop ends if a player's round count has reached 2.    
    }
    
    public int goesFirst()
    {
        Random coinFlip = new Random();
        
        return coinFlip.nextInt(2);
    }
    
    //Resolves any effect the card played might have.
    public void resolveEffect(Card playedCard, int row, int col)
    {
        char type = playedCard.getEffectType();
        char target = playedCard.getEffectTarget();
        
        //Temp variables for this method
        int validTarget;
        int[] choice = new int[2];
        
        switch(type)
        {
            case 'H':   //DISCARD HAND
                boolean discard = true; //PLACEHOLDER, ASK USER IF THEY WISH TO DISCARD
                if(discard)
                {
                    final int NEW_HAND = 7;
                    
                    if(playerTurn == 0)
                    {
                        red.discard();
                        for(int i = 0; i < NEW_HAND; i++)
                            red.draw();
                    }
                    else
                    {
                        blue.discard();
                        for(int i = 0; i < NEW_HAND; i++)
                            blue.draw();
                    }
                }
                break;
            case 'D':   //DRAW
                final int DRAW_THRESHOLD = 4;
                if(playerTurn == 0)
                {
                    if(red.getHand().size() >= 4)
                    {
                        while(red.getHand().size() < 7)
                            red.draw();
                    }
                }
                else
                {
                    if(blue.getHand().size() >= 4)
                    {
                        while(blue.getHand().size() < 7)
                            blue.draw();
                    }
                }
                break;
            case '0':   //ZERO
                if(playerTurn == 0)
                    validTarget = 1;
                else
                    validTarget = 0;
                
                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;
                while(gameBoard.tilePlayer(choice[0], choice[1]) != validTarget )
                    //REPROMPT FOR CHOICE
                    
                gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());
                break;
            case '-':   //SUBTRACT
                if(target == 'X')   //ANY
                {
                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;
                    
                    while(gameBoard.tilePlayer(choice[0], choice[1]) != -1)
                        //REPROMPT FOR CHOICE
                    
                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());    
                }
                if(target == 'O')   //OPPONENT
                {
                    if(playerTurn == 0)
                        validTarget = 1;
                    else
                        validTarget = 0;

                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;

                    while(gameBoard.tilePlayer(choice[0], choice[1]) != validTarget )
                        //REPROMPT FOR CHOICE

                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());                    
                }
            case '+':
                if(target == 'X')   //ANY
                {
                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;
                    
                    while(gameBoard.tilePlayer(choice[0], choice[1]) != -1)
                        //REPROMPT FOR CHOICE
                    
                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());    
                }
                if(target == 'O')   //OPPONENT
                {
                    if(playerTurn == 0)
                        validTarget = 1;
                    else
                        validTarget = 0;

                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;

                    while(gameBoard.tilePlayer(choice[0], choice[1]) != validTarget )
                        //REPROMPT FOR CHOICE

                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());                    
                }
                if(target == 'A')   //ADJACENT
                {
                    ArrayList<Tile> adj = gameBoard.getAdjacent(row, col);
                    
                    for(int i = 0; i < adj.size(); i++)
                        adj.get(i).addEffect(playedCard.getEffect());
                }
                if(target == 'N')
                {
                    ArrayList<Tile> nonadj = gameBoard.getNonAdjacent(row, col);

                    for(int i = 0; i < nonadj.size(); i++)
                        nonadj.get(i).addEffect(playedCard.getEffect());
                }
                break;
            case '*':
                if(target == 'X')   //ANY
                {
                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;
                    
                    while(gameBoard.tilePlayer(choice[0], choice[1]) != -1)
                        //REPROMPT FOR CHOICE
                    
                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());    
                }
                if(target == 'O')   //OPPONENT
                {
                    if(playerTurn == 0)
                        validTarget = 1;
                    else
                        validTarget = 0;

                    choice[0] = 0; //PLACEHOLDER, ASK USER FOR TARGET
                    choice[1] = 0;

                    while(gameBoard.tilePlayer(choice[0], choice[1]) != validTarget )
                        //REPROMPT FOR CHOICE

                    gameBoard.getTile(choice[0], choice[1]).addEffect(playedCard.getEffect());                    
                }
                if(target == 'A')   //ADJACENT
                {
                    ArrayList<Tile> adj = gameBoard.getAdjacent(row, col);
                    
                    for(int i = 0; i < adj.size(); i++)
                        adj.get(i).addEffect(playedCard.getEffect());
                }
                if(target == 'N')
                {
                    ArrayList<Tile> nonadj = gameBoard.getNonAdjacent(row, col);

                    for(int i = 0; i < nonadj.size(); i++)
                        nonadj.get(i).addEffect(playedCard.getEffect());
                }
                break;
        }
    }
    
    //Calculates each player's score at the end of each turn.
    public void calculateScores()
    {
        for(int i = 0; i < gameBoard.getBoardRows(); i++)
            for(int j = 0; j < gameBoard.getBoardCols(); j++)
            {
                if(gameBoard.tilePlayer(i,j) == 0)
                        currentRedScore = currentRedScore + gameBoard.tileScore(i,j);
                if(gameBoard.tilePlayer(i,j) == 1)
                        currentBlueScore = currentBlueScore + gameBoard.tileScore(i,j);
            }
    }
    
    //Calculates the bonus given by achieving the line.
    public int calculateLineBonus()
    {
        int bonus = 0;
        
        switch(line[0])
        {
            case 1:
                bonus = gameBoard.getTile(0,0).getTileValue() + gameBoard.getTile(0,1).getTileValue() + gameBoard.getTile(0,2).getTileValue();
                break;
            case 2:
                bonus = gameBoard.getTile(1,0).getTileValue() + gameBoard.getTile(1,1).getTileValue() + gameBoard.getTile(1,2).getTileValue();
                break;
            case 3:
                bonus = gameBoard.getTile(2,0).getTileValue() + gameBoard.getTile(2,1).getTileValue() + gameBoard.getTile(2,2).getTileValue();
                break;
            case 4: 
                bonus = gameBoard.getTile(0,0).getTileValue() + gameBoard.getTile(1,0).getTileValue() + gameBoard.getTile(2,0).getTileValue();
                break;
            case 5:
                bonus = gameBoard.getTile(0,1).getTileValue() + gameBoard.getTile(1,1).getTileValue() + gameBoard.getTile(2,1).getTileValue();
                break;
            case 6: 
                bonus = gameBoard.getTile(0,2).getTileValue() + gameBoard.getTile(1,2).getTileValue() + gameBoard.getTile(2,2).getTileValue();
                break;
            case 7: 
                bonus = gameBoard.getTile(0,0).getTileValue() + gameBoard.getTile(1,1).getTileValue() + gameBoard.getTile(2,2).getTileValue();
                break;
            case 8:
                bonus = gameBoard.getTile(2,0).getTileValue() + gameBoard.getTile(1,1).getTileValue() + gameBoard.getTile(0,2).getTileValue();
                break;
        }
        
        return bonus;
    }
    
    //Determines if the round has ended.
    public boolean roundEnd()
    {
        line = gameBoard.findLine();
        
        return line[0] != -1 || gameBoard.isFull();
    }
    
    public Player getCurrentPlayer(){
        if (playerTurn == 0)
            return red;
        else
            return blue;
    }//returns the player based on playerTurn

    public Board getBoard()
    {
        return gameBoard;
    }

}
