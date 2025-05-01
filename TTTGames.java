public class TTTGames
{
    private int[] weights1;
    private int[] weights2;

    private double arrayMult(int[] array1, double[] array2)
    {
        int size = array1.length;
        double sum = 0;
        for(int i = 0; i < size; i++)
        {
            sum += array1[i] * array2[i];
        }
        return sum;
    }
    
    private int arraySum(int[][] array2d)
    {
        int size = array2d[0].length;
        int sum = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                sum += array2d[i][j];
            }
        }
        return sum;
    }

    private double[] arrayInverse(double[] array)
    {
        double[] a = new double[array.length];
        for(int i = 0; i < array.length; i++)
        {
            a[i] = array[i] * -1;
        }
        return a;
    }

    public double[] train(int size, double[] initialW1, double[] initialW2, double learningRate)
    {
        double r = learningRate;
        double[] w1 = initialW1;
        double[] w2 = initialW2;
        int count = 0;
        int max = 0;
        double n = 0;
        double sign = 0;
        double index = 0;
        for(int x = 0; x < 10000; x++)
        {
            //Only Modifiing Weights for Player 2
            for(int i = 0; i < w2.length; i++)
            {
                sign = Math.random();
                n = Math.random() * r;
                //if you roll a negative sign
                if(sign < 0.5)
                {
                    //w2 should be -,+,-,+
                    if((i+2)%2==0)
                        w2[i] += n * -1;
                    else if(w2[i] - n > 0)
                    {
                        w2[i] += n * -1;
                    }
                }
                //positive sign
                else
                {
                    if((i+2)%2==1)
                        w2[i] += n;
                    else if(w2[i] + n < 0)
                    {
                        w2[i] += n;
                    }
                }
            }
            //Play a game
            //System.out.print("\nGame: " + x);
            int z = playGame(size, w1, w2);
            
            /*
            System.out.println(z);
            for(int y = 0; y < w1.length; y++)
            {
                System.out.print(w1[y] + " ");
            }
            System.out.println();
            for(int y = 0; y < w1.length; y++)
            {
                System.out.print(w2[y] + " ");
            }
            System.out.println("\n*******************************");
            */

            if(z==1)
                w2 = arrayInverse(w1);
            else if(z==2)
                w1 = arrayInverse(w2);
        }
        return w1;
    }

    //w1 are players 1's weights that determine how they play
    public int playGame(int size, double[] w1, double[] w2)
    {
        BoardExtraction be = new BoardExtraction();
        int win;
        int winner = 0;
        if(size%2 == 0)
            win = size/2 + 1;
        else
            win = size/2 + 2;
        int[][] board = new int[size][size];
        int[] features;
        boolean done = false;
        double max = -99999999;
        int index1 = 0;
        int index2 = 0;
        double sum = 0;
        //loop until whole board is full
        for(int x = 0; x < Math.pow(size, 2) / 2; x++)
        {
        //For Player 1 and Player 2
        for(int p = 1; p < 3; p++)
        {
            for(int i = 0; i < size; i++)
            {
                //Look for best Move
                for(int j = 0; j < size; j++)
                {
                    //perform feature extraction assuming player p plays at (i,j)
                    if(board[i][j] == 0)
                    {
                        board[i][j] = p;
                        features = be.countWins(board);
                        if(p == 1)
                            sum = arrayMult(features, w1);
                        else
                            sum = arrayMult(features, w2);
                        if(sum > max)
                        {
                            max = sum;
                            index1 = i;
                            index2 = j;
                        }
                        //return board state back
                        board[i][j] = 0;
                    }
                }
            }
            //To account for player 1 going first and last
            if(x < (Math.pow(size, 2) / 2 - 1) || p == 1)
            {
                //Play best move
                board[index1][index2] = p;
                //System.out.print("P" + p + "(" + index1 + ", " + index2 + ")  ");
            }
            max = -999999999;
            //Check to see if someone won
            features = be.countWins(board);
            if(features[2*win - 2] > 0)
            {
            winner = 1;
            p += 2;
            x += 10000000;
            }
            if(features[2*win - 1] > 0)
            {
            winner = 2;
            p += 2;
            x += 1000000;
            }
        }
        }
        return winner;
    }
}