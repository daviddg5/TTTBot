public class BoardExtraction 
{
    public int[][] board;
    private int win;
    private int size;
    public BoardExtraction()
    {
    }

    //Very Important, Essially our Feature Extraction
    //Reads the Board State and Counts # of ways Player 1 or 2 Can win
    // Feature Vector = {# of unblocked single x's, # of unblocked single 0's, # of unblocked double x's ...}
    // Automatically Scales to Different Board Sizes
    // Ex. a 5x5 needs 4 to win so it counts up to quadruples for each player
    //Feature Vector Length = 2 times # in a row to win
    public int[] countWins(int[][] tttBoard)
    {
        board = tttBoard;
        size = board[0].length;
        win = size/2 + 2;
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

    private int[] orthoWins(int player, int direction)
    {

        int[] orthoWins = new int[win];
        int count = 0;
        int maxRun = 0;
        int distance = 1;
        boolean distOn = false;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                //if direction = 0 count all the horizontal wins
                if(direction == 0)
                {
                    if(board[i][j] == 0)
                    {
                        if(distOn)
                            distance ++;
                        maxRun++;

                    }
                    else if(board[i][j] == (player + 1))
                    {
                        if(distance < win)
                        {
                            maxRun++;
                            count++;
                        }
                        distance = 1;
                        distOn = true;
                    }
                    else if(maxRun < win)
                        maxRun = 0;
                }
                //if direction = 1 count all the vertical wins
                else if(direction == 1)
                {
                    if(board[j][i] == 0)
                    {
                        if(distOn)
                            distance++;
                        maxRun++;
                    }
                    else if(board[j][i] == (player + 1))
                    {
                        if(distance < win)
                        {
                            maxRun++;
                            count++;
                        }
                        distance = 1;
                        distOn = true;
                    }
                    else if(maxRun < win)
                        maxRun = 0;
                }
            }
            if(maxRun >= win && count > 0)
            {
                if(count < win)
                    orthoWins[count-1] = orthoWins[count-1] + 1;
                //double check all in a row to win
                else
                {
                    maxRun = 0;
                    if(direction == 0)
                    {
                        for(int x = 0; x < size; x++)
                        {
                            if(board[i][x] == (player + 1))
                                {
                                    count++;
                                    maxRun = count;
                                }
                            else
                                count = 0;
                        }
                        if(maxRun >= win)
                            orthoWins[win-1] = orthoWins[win-1] + 1;
                        else
                            orthoWins[0] = orthoWins[0] + maxRun;
                    }
                    else if(direction == 1)
                    {
                        for(int x = 0; x < size; x++)
                        {
                            if(board[x][i] == (player + 1))
                            {
                                count++;
                                maxRun = count;
                            }
                            else
                                count = 0;
                        }
                        if(maxRun >= win)
                            orthoWins[win-1] = orthoWins[win-1] + 1;
                        else
                            orthoWins[0] = orthoWins[0] + maxRun;
                    }
                }
            }
            count = 0;
            maxRun = 0;
            distance = 1;
            distOn = false;
        }
        return orthoWins;
    }

    private int[] diagnalWins1(int player)
    {
        int[] diagWins = new int[win];
        int[] diagLine = new int[size];
        int count = 0;
        int maxRun = 0;
        int distance = 0;
        boolean distOn = false;
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
            //read off the line to count how many in a row
            for(int i = 0; i < size - absValOffset; i++)
            {
                if(diagLine[i] == 0)
                {
                    if(distOn)
                        distance++;
                    maxRun++;
                }
                else if(diagLine[i] == (player + 1))
                {
                    if(distance < win)
                    {
                        maxRun++;
                        count++;
                    }
                    distance = 1;
                    distOn = true;
                }
                else if(maxRun < win)
                        maxRun = 0;
            }
            if(maxRun >= win && count > 0)
            {
                if(count < win)
                    diagWins[count-1] = diagWins[count-1] + 1;
                //for a win, double check there all in a row
                else
                {
                    count = 0;
                    maxRun = 0;
                    for(int x = 0; x < size - absValOffset ;x++)
                    {
                        if(diagLine[x] == (player + 1))
                        {
                            count++;
                            maxRun = count;
                        }
                        else
                            count = 0;
                    }   
                    if(maxRun >= win)
                        diagWins[win-1] = diagWins[win-1] + 1;
                    else
                        diagWins[0] = diagWins[0] + maxRun;
                }
            }
            count = 0;
            maxRun = 0;
            distance = 1;
            distOn = false;
        }
        return diagWins;
    }

    private int[] diagnalWins2(int player)
    {
        int[] diagWins = new int[win];
        int[] diagLine = new int[size];
        int count = 0;
        int maxRun = 0;
        int distance = 1;
        boolean distOn = false;
        boolean isWin = false;
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
            //read off our line to count how many in a row
            for(int i = 0; i < size - absValOffset; i++)
            {
                if(diagLine[i] == 0)
                {
                    if(distOn)
                        distance++;
                    maxRun++;
                }
                else if(diagLine[i] == (player + 1))
                {
                    if(distance < win)
                    {
                        maxRun++;
                        count++;
                    }
                    distance = 1;
                    distOn = true;
                }
                else if(maxRun < win)
                        maxRun = 0;
            }
            if(maxRun >= win && count > 0)
            {
                if(count != (win - 1))
                    diagWins[count-1] = diagWins[count-1] + 1;
                //for a win, double check there all in a row
                else
                {
                    count = 0;
                    maxRun = 0;
                    for(int x = 0; x < size - absValOffset ;x++)
                    {
                        if(diagLine[x] == (player + 1))
                        {
                            count++;
                            maxRun = count;
                        }
                        else
                            count = 0;
                    }   
                    if(maxRun >= win)
                        diagWins[win-1] = diagWins[win-1] + 1;
                    else
                        diagWins[0] = diagWins[0] + maxRun;
                }
            }
            count = 0;
            maxRun = 0;
            distance = 1;
            distOn = false;
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
