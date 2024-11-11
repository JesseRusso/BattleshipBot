/*
 * COSC 111 - L05
 * Jesse Russo 300180982
 * Lab 8 - Battleship Part 1
 * 
 * A basic version of Battleship where you guess the location of your opponents boats until you win by sinking them all.
 */
import java.util.Arrays;
import java.util.Scanner;
public class Battleship 
{
    //This is the main game method, holding the player and opponent game boards and game loop.
    public static void main(String[] args)
    {   
        char[][] shotsFired = new char[6][6];
        int[][] oppBoard = 
            {{2, 0, 0, 0, 0, 0}, 
            {2, 0, 0, 0, 3, 0}, 
            {0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 3, 0}, 
            {0, 0, 0, 0, 0, 0},
            {0, 0, 4, 4, 4, 4}};
        initializeBoards(shotsFired);
        showBoard(shotsFired);
        Scanner input = new Scanner(System.in);
        
        //Main game loop. Loops until there are no more ships in oppBoard.
        boolean gameOver = false;
        while(!gameOver)
        {
            System.out.print("\nPlease enter a guess in the form 'B5': ");
            String guess = input.next().toUpperCase();
            if(validateGuess(guess))
            {
                int row = guess.charAt(0)-'A';
                int col = Integer.parseInt(guess.substring(1))-1;
                guess(row, col, oppBoard, shotsFired);
                showBoard(shotsFired);
                gameOver = winCheck(oppBoard, shotsFired);
            }
        }
    }
    /*Called when the player fires a shot. Checks the guessed cell for ships and alerts the user of a hit or a miss.
     *If the shot is a hit, the health of the hit ship is the remaining number of cells matching shipSize.
     *The ship is sunk if no cells on the board match shipSize.
     */
    public static void guess(int row, int col, int[][] oppBoard, char[][] shotsFired)
    {
        if(oppBoard[row][col] > 0)
        {
            int shipSize = oppBoard[row][col];
            oppBoard[row][col] = 0;
            shotsFired[row][col] = 'X';
            int shipHP = 0;
            System.out.println((char)(row + 'A') + "" + (col + 1) + " is a hit");

            //Checks for remaining values of shipSize to add to shipHP
            for(int i = 0; i < oppBoard.length; i++)
            {
                for( int j = 0; j < oppBoard[i].length; j++)
                {
                    if(oppBoard[i][j] == shipSize)
                    {
                        shipHP++;
                    }
                }
            }
            if(shipHP == 0)
            {
                String[] boats = {"Patrol Boat", "Destroyer", "Battleship"};
                System.out.println("You sunk the " + boats[shipSize - 2] + "!\n");
            }
        }
        else
        {
            shotsFired[row][col] = 'O';
            System.out.println("miss");
        }
    }
    //Displays the player's shotsFired board showing hits and misses.
    public static void showBoard(char[][] shotsFired)
    {
        System.out.println("    1   2   3   4   5   6");
        System.out.println("   ___ ___ ___ ___ ___ ___");
        
        for(int i = 0; i < shotsFired.length; i++)
        {
            String line = (char)(i + 'A') + " |";
            for(int j = 0; j < shotsFired[i].length; j++)
            {
                line = line + "_" + shotsFired[i][j] + "_|";
            }
            System.out.println(line);
        }
    }
    //Checks for win by looking for ship values in the opponents game board.
    public static boolean winCheck(int[][] oppBoard, char[][] shotsFired)
    {
        for(int[] col : oppBoard)
        {
            for(int cell : col)
            {
                if(cell > 0)
                {
                    return false;
                }
            }
        }
        System.out.println("Game over. You win!");
        return true;
    }
    /*Fills the player's shotsFired board with empty values at the start of the game. 
     *Will be more useful later when working with different placements of ships on oppBoard.
     */
    public static void initializeBoards(char[][] shots)
    {
        for (char[] row : shots) 
        {
            Arrays.fill(row, '_');
        }
    }
    //Validates user input is within acceptable range.
    public static boolean validateGuess(String guess)
    {
        char row = Character.toUpperCase(guess.charAt(0));
        char col = Character.toUpperCase(guess.charAt(1));
        return (guess.length() == 2 && row >= 'A' && row <= 'F' && col >= 48 && col <= 54);
    }
}
