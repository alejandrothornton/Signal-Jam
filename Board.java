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
public class Board {
    
    private final int ROWS = 3;
    private final int COLS = 3;
    
    Tile[][] gameBoard;
    
    //Generates new board of empty tiles.
    public Board()
    {
        gameBoard = new Tile[ROWS][COLS];
        
        for(int i = 0; i < gameBoard.length; i++)
            for(int j = 0; j < gameBoard[i].length; j++)
                gameBoard[i][j] = new Tile();
    }
    
    //Checks if the placement of a card is valid.
    //If it is, place card and return true.
    //If not, return false.
    public boolean placeTile(Card playedCard, int player, int row, int column)
    {
        if(gameBoard[row][column].isEmpty())
        {
            gameBoard[row][column].placeTile(playedCard, player);
            return true;
        }
        
        return false;
    }
    
    //Clears the board
    public void clearBoard(Player player1, Player player2)
    {
        for(int i = 0; i < gameBoard.length; i++)
            for(int j = 0; j < gameBoard[i].length; j++)
            {
                if(!gameBoard[i][j].isEmpty())
                {
                    if(gameBoard[i][j].getPlayer() == 0)
                        player1.getDiscard().add(gameBoard[i][j].getPlayedCard());
                    else if(gameBoard[i][j].getPlayer() == 1)
                        player2.getDiscard().add(gameBoard[i][j].getPlayedCard());
                }    
                    gameBoard[i][j].clearTile();
            }               
    }
    
    //Method for finding when a line has been laid down.
    //Output format, two ints: [line, player]
    /*  LINE ENUMERATION
        1: ROW 1 HORIZONTAL
        2: ROW 2 HORIZONTAL
        3: ROW 3 HORIZONTAL
        4: COL 1 VERTICAL
        5: COL 2 VERTICAL
        6: COL 3 VERTICAL
        7: TOP LEFT TO BOTTOM RIGHT DIAGONAL
        8: BOTTOM LEFT TO TOP RIGHT DIAGONAL
    */
    public int[] findLine()
    {
        //ROW 1 HORIZONTAL
        if(gameBoard[0][0].getPlayer() == gameBoard[0][1].getPlayer() && gameBoard[0][1].getPlayer() == gameBoard[0][2].getPlayer())
            if(gameBoard[0][0].getPlayer() != -1)
            {
                int[] line = {1,gameBoard[0][0].getPlayer()};
                return line;
            }
        
        //ROW 2 HORIZONTAL
        if(gameBoard[1][0].getPlayer() == gameBoard[1][1].getPlayer() && gameBoard[1][1].getPlayer() == gameBoard[1][2].getPlayer())
            if(gameBoard[1][0].getPlayer() != -1)
            {
                int[] line = {2,gameBoard[1][0].getPlayer()};
                return line;
            }
        
        //ROW 3 HORIZONTAL
        if(gameBoard[2][0].getPlayer() == gameBoard[2][1].getPlayer() && gameBoard[2][1].getPlayer() == gameBoard[2][2].getPlayer())
            if(gameBoard[2][0].getPlayer() != -1)
            {
                int[] line = {3,gameBoard[2][0].getPlayer()};
                return line;
            }
        
        //COL 1 VERTICAL
        if(gameBoard[0][0].getPlayer() == gameBoard[1][0].getPlayer() && gameBoard[1][0].getPlayer() == gameBoard[2][0].getPlayer())
            if(gameBoard[0][0].getPlayer() != -1)
            {
                int[] line = {4,gameBoard[0][0].getPlayer()};
                return line;
            }
        
        //COL 2 VERTICAL
        if(gameBoard[0][1].getPlayer() == gameBoard[1][1].getPlayer() && gameBoard[1][1].getPlayer() == gameBoard[2][1].getPlayer())
            if(gameBoard[0][1].getPlayer() != -1)
            {
                int[] line = {5,gameBoard[0][1].getPlayer()};
                return line;
            }
        
        //COL 3 VERTICAL
        if(gameBoard[0][2].getPlayer() == gameBoard[1][2].getPlayer() && gameBoard[1][2].getPlayer() == gameBoard[2][2].getPlayer())
            if(gameBoard[0][2].getPlayer() != -1)
            {
                int[] line = {6,gameBoard[0][2].getPlayer()};
                return line;
            }
        
        //TOP LEFT TO BOTTOM RIGHT DIAGONAL
        if(gameBoard[0][0].getPlayer() == gameBoard[1][1].getPlayer() && gameBoard[1][1].getPlayer() == gameBoard[2][2].getPlayer())
            if(gameBoard[0][0].getPlayer() != -1)
            {
                int[] line = {7,gameBoard[0][0].getPlayer()};
                return line;
            }
        
        //BOTTOM LEFT TO TOP RIGHT DIAGONAL
        if(gameBoard[2][0].getPlayer() == gameBoard[1][1].getPlayer() && gameBoard[1][1].getPlayer() == gameBoard[0][2].getPlayer())
            if(gameBoard[2][0].getPlayer() != -1)
            {
                int[] line = {8,gameBoard[2][0].getPlayer()};
                return line;
            }
        
        int[] line = {-1,-1};
        return line;
    }
    
    public boolean isFull()
    {
        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++)
                if(gameBoard[i][j].isEmpty())
                    return false;
        
        return true;
    }
    
    public ArrayList<Tile> getAdjacent(int row, int col)
    {
        ArrayList<Tile> adjacents = new ArrayList(0);
        
        if(row-1 > 0 && col-1 > 0 && row-1 < ROWS && col-1 < COLS)  //TOP LEFT
            adjacents.add(gameBoard[row-1][col-1]);

        if(row-1 > 0 && col > 0 && row-1 < ROWS && col < COLS)      //TOP
            adjacents.add(gameBoard[row-1][col]);

        if(row-1 > 0 && col+1 > 0 && row-1 < ROWS && col+1 < COLS)  //TOP RIGHT
            adjacents.add(gameBoard[row-1][col+1]);

        if(row > 0 && col-1 > 0 && row < ROWS && col-1 < COLS)  //LEFT
            adjacents.add(gameBoard[row][col-1]);

        if(row > 0 && col+1 > 0 && row < ROWS && col+1 < COLS)  //RIGHT
            adjacents.add(gameBoard[row][col+1]);

        if(row+1 > 0 && col-1 > 0 && row+1 < ROWS && col-1 < COLS)  //BOTTOM LEFT
            adjacents.add(gameBoard[row+1][col-1]);

        if(row+1 > 0 && col > 0 && row+1 < ROWS && col < COLS)      //BOTTOM
            adjacents.add(gameBoard[row+1][col]);

        if(row+1 > 0 && col+1 > 0 && row+1 < ROWS && col+1 < COLS)  //BOTTOM RIGHT
            adjacents.add(gameBoard[row+1][col+1]);

        return adjacents;
    }
    
    public ArrayList<Tile> getNonAdjacent(int row, int col)
    {
        ArrayList<Tile> nonadjacents = new ArrayList(0);
        
        ArrayList<Tile> adjacents = getAdjacent(row, col);
        
        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++)
                if(!adjacents.contains(gameBoard[i][j]))
                    if(i != row && j != col)
                        nonadjacents.add(gameBoard[i][j]);
        
        return nonadjacents;
    }
    
    public Tile getTile(int row, int col)
    {
        return gameBoard[row][col];
    }
    
    //GETTERS FOR TILE INFORMATION
    public int tilePlayer(int row, int col)
    {
        return gameBoard[row][col].getPlayer();
    }
    
    public int tileScore(int row, int col)
    {
        return gameBoard[row][col].getTileValue();
    }
    
    //BASIC GETTERS
    public int getBoardRows()
    {
        return ROWS;
    }
    
    public int getBoardCols()
    {
        return COLS;
    }
}
