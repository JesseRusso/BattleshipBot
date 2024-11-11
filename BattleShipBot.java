public class BattleShipBot {
    public static void main(String[] args)
    {
        System.out.println(0%2);
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
                    target = true;
                    row = 'A';
                    col = j;
                }
            }
        }
        if(target)
        {
            targetShot(guesses, row, col);
        }
        else
        {

        } 
        
        return "";
    }
    private static void probMap(char[][] guesses)
    {

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
        char row = 'A';
        int col = 1;
        //Check every odd pairity cell for hits
        for(int i = 0; i < guesses.length; i++)
        {
            for(int j = 0; j < guesses[i].length; j += 2)
            {
                if(i % 2 == 0) j++;
                if(guesses[i][j] == '.')
                {
                    row += i;
                    col +=j;
                    return (char)row + col;
                }
            }
        }
    }
    private static void targetShot(char[][] guesses, int row, int col)
    {

    }
}
