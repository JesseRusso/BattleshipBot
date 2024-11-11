import java.util.Arrays;

public class BattleShipBot {
    
    public static void main(String[] args)
    {
        char[][] testBoard = new char[10][10];
        for(char[] row : testBoard)
        {
            Arrays.fill(row, '.');
        }
        testBoard[0][1] = 'X';
        testBoard[0][2] = 'X';
        testBoard[0][3] = '3';
        probMap(testBoard);
    }

    public static String makeGuess(char[][] guesses)
    {
        boolean target = false;
        int row;
        int col;
        String guess;
        for(int i = 0; i < guesses.length; i++)
        {
            for(int j = 0; j < guesses[i].length; j ++)
            {
                if(guesses[i][j] == 'X')
                {
                    return targetShot(guesses, i, j);
                }
            }
        }
        return huntingShot(guesses);
    }

    /*Builds a probability density map of possible ship positions
     * 
     * 
     */
    private static void probMap(char[][] guesses)
    {
        int[] ships = {2, 3, 3, 4, 5};
        int[][] distribution = new int[10][10];
        for(int ship : ships)
        {
            //check each row for space to fit a ship and increment the value of the cells that a ship can occupy. Does this for each ship size and builds a probability distrobution.
            for(int i = 0; i < guesses.length; i++)
            {
                //row
                for(int j = 0; j < guesses[i].length - ship; j++)
                {
                    //begin cell
                    if(guesses[i][j] == '.')
                    {
                        //boolean fits = true;
                        int freeCellCount = 0;
                        for(int k = 1; k <= ship; k++)
                        {
                            if(guesses[i][j+k] == '.' || guesses[i][j+k] == 'X')
                            {
                                freeCellCount++;
                            }
                        }
                        if(freeCellCount == ship)
                        {
                            for(int e = 0; e <= ship; e++)
                            {
                                distribution[i][j+e]++;
                            }
                        }
                    }
                }
            }
            //Same as above but for columns now
            for(int i = 0; i < guesses.length-ship; i++)
            {
                //row
                for(int j = 0; j < guesses[i].length; j++)
                {
                    //begin cell
                    if(guesses[i][j] == '.')
                    {
                        int freeCellCount = 0;
                        //boolean fits = false;
                        for(int k = 1; k <= ship; k++)
                        {
                            if(guesses[i+k][j] == '.' || guesses[i+k][j] == 'X')
                            {
                                freeCellCount++;
                            }
                        }
                        if(freeCellCount == ship)
                        {
                            for(int e = 0; e <= ship; e++)
                            {
                                distribution[i+e][j]++;
                            }
                        }
                    }
                }
            }
        }
        showBoard(distribution);
    }
    private static void getMinShipSize(char[][] guesses)
    {

    }
    private static void parityCheck(char[][] guesses)
    {
        for(int i = 0; i < guesses.length; i++)
        {

        }
    }
    private static String huntingShot(char[][] guesses)
    {
        String guess = "";
        char row = 'A';
        int col = 1;
        //Check every odd parity cell for hits
        for(int i = 0; i < guesses.length; i++)
        {
            for(int j = 0; j < guesses[i].length; j += 2)
            {
                if(i % 2 == 0) j++;
                if(guesses[i][j] == '.')
                {
                    row += i;
                    col +=j;
                    guess += (char)row + col;
                    return guess;
                }
            }
        }
        return guess;
    }
    private static String targetShot(char[][] guesses, int row, int col)
    {

        return "B5";
    }
    public static void showBoard(int[][] probMap)
    {
        System.out.println("    1   2   3   4   5   6   7   8   9   10");
        System.out.println("   ___ ___ ___ ___ ___ ___ ___ ___ ___ ____");
        
        for(int i = 0; i < probMap.length; i++)
        {
            String line = (char)(i + 'A') + " |";
            for(int j = 0; j < probMap[i].length; j++)
            {
                line = line + "_" + probMap[i][j] + "_|";
            }
            System.out.println(line);
        }
    }
    public static void showBoard(char[][] shotsFired)
    {
        System.out.println("    1   2   3   4   5   6   7   8   9   10");
        System.out.println("   ___ ___ ___ ___ ___ ___ ___ ___ ___ ____");
        
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
}