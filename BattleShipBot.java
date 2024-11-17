import java.util.Arrays;

public class BattleShipBot {
    
    public static void main(String[] args)
    {
        char[][] testBoard = new char[10][10];
        for(char[] row : testBoard)
        {
            Arrays.fill(row, '.');
        }
    }

    public static String makeGuess(char[][] guesses)
    {
        int[][] map = probMap(guesses);
        int row = 0; 
        int col = 0; 
        int maxValue = 0;
        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++)
            {
                if(map[i][j] > maxValue)
                {
                    maxValue = map[i][j];
                    row = i;
                    col = j;
                }
            }
        }
        String guess = (char)(row + 'A') + "" + (int)(col+1);
        //System.out.println("2% plz");
        //showBoard(map);
        return guess;
    }

    /*Builds a probability density map of possible ship positions
     * 
     * 
     */
    private static int[][] probMap(char[][] guesses)
    {
        int[] ships = {2, 3, 3, 4, 5};
        int[][] distribution = new int[10][10];
        for(int ship : ships)
        {
            //check each row for space to fit a ship and increment the value of the cells that a ship can occupy. Does this for each ship size and builds a probability distrobution.
            for(int i = 0; i < guesses.length; i++)
            {
                //loops over each cell in a row
                for(int j = 0; j <= guesses[i].length - ship; j++)
                {
                    char startCell = guesses[i][j];
                    boolean[] fitsHits = fitsRow(guesses, i, j, ship);
                    if(startCell == 'X' || startCell == '.' && fitsHits[0])
                    {
                        for(int l = 0; l < ship; l++)
                        {
                            distribution[i][j + l]++;
                        }
                        if(fitsHits[1])
                        {
                            hitRow(guesses, distribution, i, j, ship);
                        }
                    }
                }
            }
            //Same as above but for columns now
            for(int i = 0; i <= guesses.length-ship; i++)
            {
                //col
                for(int j = 0; j < guesses[i].length; j++)
                {
                    char startCell = guesses[i][j];
                    boolean[] fitsHits = fitsCol(guesses, i, j, ship);
                    if(startCell == 'X' || startCell == '.' && fitsHits[0])
                    {
                        for(int l = 0; l < ship; l++)
                        {
                            distribution[i + l][j]++;
                        }
                        if(fitsHits[1])
                        {
                            hitCol(guesses, distribution, i, j, ship);
                        }
                    }
                }
            }
            //Clear the map of values where there are misses
            for(int i = 0; i < guesses.length; i++)
            {
                for(int j = 0; j < guesses[i].length; j++)
                {
                    if(guesses[i][j] == 'O')
                    {
                        distribution[i][j] = 0;
                    }
                }
            }

        }
        return distribution;
    }
    private static void getMinShipSize(char[][] guesses)
    {
        int threeCount = 0;
        for(int i = 2; i < 6; i++)
        {
            
        }
    }
    //Weights the cells around a hit in a row by FACTOR
    private static void hitRow(char[][] guesses, int[][] map, int row, int col, int shipSize)
    {
        final int FACTOR = 2;
        for(int j = 0; j < shipSize; j++)
        {
            if(guesses[row][col + j] == 'X')
            {
                //Don't go past the edges
                if(col + j == 0)
                {
                    map[row][col + 1] *= FACTOR;
                }
                else if(col + j == guesses[row].length - 1)
                {
                    map[row][col + j - 1] *= FACTOR;
                }
                else
                {
                    map[row][col + j - 1] *= FACTOR;
                    map[row][col + j + 1] *= FACTOR;
                }
                //Zero the hit cell
                map[row][col + j] = 0;
            }
        }
    }
    //Weights the cells around a hit in a column by FACTOR.
    private static void hitCol(char[][] guesses, int[][] map, int row, int col, int shipSize)
    {
        final int FACTOR = 2;
        for(int j = 0; j < shipSize; j++)
        {
            if(guesses[row + j][col] == 'X')
            {
                //Don't go past the edges 
                if(row + j == 0)
                {
                    map[row + 1][col] *= FACTOR;
                }
                else if(row + j == guesses.length - 1)
                {
                    map[row + j - 1][col] *= FACTOR;
                }
                else
                {
                    map[row + j - 1][col] *= FACTOR;
                    map[row + j + 1][col] *= FACTOR;
                }
                //Zero the hit cell
                map[row + j][col] = 0;
            }
        }
    }
    /*Checks if a ship of length shipSize will fit in a row starting from guesses[i][j] 
     *Returns a boolean array with [0] == true if there is room for a ship and [1] == true if there is a hit in the path 
     */
    private static boolean[] fitsRow(char[][] guesses, int row, int col, int shipSize)
    {   
            int freeCellCount = 0;
            boolean hitsNearby = false;
            for(int k = 0; k < shipSize; k++)
            {
                char cellToCheck = guesses[row][col + k];
                if(cellToCheck == '.')
                {
                    freeCellCount++;
                }
                else if(cellToCheck == 'X')
                {
                    hitsNearby = true;
                    freeCellCount++;
                }
            }
            return new boolean[] {freeCellCount == shipSize, hitsNearby};
    }
    /*Checks if a ship of length shipSize will fit in a column starting from guesses[i][j] 
     *Returns a boolean array with [0] == true if there is room for a ship and [1] == true if there is a hit in the path 
     */
    private static boolean[] fitsCol(char[][] guesses, int row, int col, int shipSize)
    {
        int freeCellCount = 0;
        boolean hitsNearby = false;
        for(int k = 0; k < shipSize; k++)
        {
            char cellToCheck = guesses[row + k][col];
            if(cellToCheck == '.')
            {
                freeCellCount++;
            }
            else if(cellToCheck == 'X')
            {
                freeCellCount++;
                hitsNearby = true;
            }
        }
        return new boolean[] {freeCellCount == shipSize, hitsNearby};
    }
    //Method to show the state of probMap
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
    //Method to show the state of the shotsFired board
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