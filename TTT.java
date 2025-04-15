public class TTT 
{
    public int[][] board;
    public int win;
    public int size;
    public TTT(int boardSize)
    {
        board = new int[size][size];
        size = boardSize;
        if(size%2 == 0)
            win = size/2 + 1;
        else
            win = size/2 + 2;
    }

    public int[] countWins()
    {
        int[] vertWins1 = orthoWins(0, 0);
        int[] vertWins2 = orthoWins(1, 0);
        int[] horWins1 = orthoWins(0, 1);
        int[] horWins2 = orthoWins(1, 1);
        int[] diagWins1 = diagnalWins1(0);
        int[] diagWins2 = diagnalWins2(0);
        int[] diagWins3 = diagnalWins1(1);
        int[] diagWins4 = diagnalWins2(1);
        int[] diagWins = arrayWeave(arraySum(diagWins1, diagWins2), arraySum(diagWins3, diagWins4));
        int[] vertWins = arrayWeave(vertWins1, vertWins2);
        int[] horWins = arrayWeave(horWins1, horWins2);
        int[] totalWins = arraySum(vertWins, arraySum(horWins, diagWins));
        return totalWins;
    }

    public int[] orthoWins(int player, int direction)
    {

        int[] orthoWins = new int[win];
        int count = 0;
        int maxRun = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                //if direction = 0 count all the vertical wins
                if(direction == 0)
                {
                    if(board[i][j] == 0)
                    maxRun ++;
                    else if(board[i][j] == (player + 1))
                    {
                        maxRun++;
                        count++;
                    }
                    else maxRun = 0; 
                }
                //if direction = 1 count all the horizaontal wins
                else if(direction == 1)
                {
                    if(board[j][i] == 0)
                    maxRun ++;
                    else if(board[j][i] == (player + 1))
                    {
                        maxRun++;
                        count++;
                    }
                    else maxRun = 0; 
                }
            }
            if(maxRun >= win && count > 0)
                orthoWins[count-1] = orthoWins[count-1] + 1;
            count = 0;
            maxRun = 0;
        }
        return orthoWins;
    }

    public int[] diagnalWins1(int player)
    {
        int[] diagWins = new int[win];
        int[] diagLine = new int[size];
        int count = 0;
        int maxRun = 0;
        int end = size/2 - 1;
        for(int offset = -1 * end; offset <= end; offset++)
        {
            int absValOffset = 0;
            if(offset < 0)
            absValOffset = offset * -1;
            else absValOffset = offset;
            //Set up our line in an array
            for(int i = 0; i < size - absValOffset; i++)
            {
                
                if(offset < 0)
                diagLine[i] = board[i][i-offset];
                else
                diagLine[i] = board[i+offset][i];
            }
            //
            for(int i = 0; i < size - absValOffset; i++)
            {
                if(diagLine[i] == 0)
                maxRun++;
                else if(diagLine[i] == (player + 1))
                {
                    maxRun++;
                    count++;
                }
                else maxRun = 0;
            }
            if(maxRun >= win && count > 0)
                diagWins[count-1] = diagWins[count-1] + 1;
            count = 0;
            maxRun = 0;
        }
        return diagWins;
    }

    public int[] diagnalWins2(int player)
    {
        int[] diagWins = new int[win];
        int[] diagLine = new int[size];
        int count = 0;
        int maxRun = 0;
        int end = size/2 - 1;
        for(int offset = -1 * end; offset <= end; offset++)
        {
            int absValOffset = 0;
            if(offset < 0)
            absValOffset = offset * -1;
            else absValOffset = offset;

            //Set up our line in an array
            for(int i = 0; i < size - absValOffset; i++)
            {
                if(offset < 0)
                diagLine[i] = board[board.length - i - 1][i-offset];
                else
                diagLine[i] = board[board.length - i - offset - 1][i];
            }
            //
            for(int i = 0; i < size - absValOffset; i++)
            {
                if(diagLine[i] == 0)
                maxRun++;
                else if(diagLine[i] == (player + 1))
                {
                    maxRun++;
                    count++;
                }
                else maxRun = 0;
            }
            if(maxRun >= win && count > 0)
                diagWins[count-1] = diagWins[count-1] + 1;
            count = 0;
            maxRun = 0;
        }
        return diagWins;
    }

    private int[] arrayWeave(int[] array1, int[] array2)
    {
        int[] array3 = new int[array1.length + array2.length];
        int j = 0;
        for(int i = 0; i < array1.length; i++)
        {
            array3[j] = array1[i];
            j++;
            array3[j] = array2[i];
            j++;
        }
        return array3;
    }

    private int[] arraySum(int[] array1, int[] array2)
    {
        int[] array3 = new int[array1.length];
        for(int i = 0; i < array1.length; i++)
        {
            array3[i] = array1[i] + array2[i];
        }
        return array3;
    }

}
