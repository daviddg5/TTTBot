import java.util.Scanner;
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
        return w2;
    }

    //w1 are players 1's weights that determine how they play
    public int playGame(int size, double[] w1, double[] w2)
    {
        BoardExtraction be = new BoardExtraction();
        int win = size/2 + 2;
        int winner = 0;
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
                            index1 = i;
                            index2 = j;
                            //System.out.println("Player " + p + ", " + "Old Max: " + max + ", New Max: " + sum + ", Index ( " + index1 + ", " + index2 + ")");
                            max = sum;
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

    int playHuman(int size, double[] weights)
    {
        BoardExtraction be = new BoardExtraction();
        int win = size/2 + 2;
        int winner = 0;
        int[][] board = new int[size][size];
        int[] features;
        boolean done = false;
        double max = -99999999;
        int index1 = 0;
        int index2 = 0;
        double sum = 0;
        Scanner scan = new Scanner(System.in);
        //loop until whole board is full
        for(int x = 0; x < Math.pow(size, 2) / 2; x++)
        {
            //human goes
            System.out.println("Please enter your move x coordinate");
            int a = scan.nextInt();
            System.out.println("Please enter your move y coordinate");
            int b = scan.nextInt();
            board[a][b] = 1;
            displayBoard(board);
            features = be.countWins(board);
            if(features[2*win - 2] > 0)
            {
                winner = 1;
                x = 100000;
                done = true;
            }
            //computer goes
            if(!done)
            for(int i = 0; i < size; i++)
                {
                    //Look for best Move
                    for(int j = 0; j < size; j++)
                    {
                        //perform feature extraction assuming player p plays at (i,j)
                        if(board[i][j] == 0)
                        {
                            board[i][j] = 2;
                            features = be.countWins(board);
                            sum = arrayMult(features, weights);
                            if(sum > max)
                            {
                                index1 = i;
                                index2 = j;
                                //System.out.println("Player " + p + ", " + "Old Max: " + max + ", New Max: " + sum + ", Index ( " + index1 + ", " + index2 + ")");
                                max = sum;
                            }
                            //return board state back
                            board[i][j] = 0;
                        }
                    }
                }
            board[index1][index2] = 2;
            displayBoard(board);
            max = -999999;
            features = be.countWins(board);
            if(features[2*win - 1] > 0)
            {
                winner = 2;
                x = 100000;
            }
        }
        scan.close();
        return winner;
    }
    void displayBoard(int[][] board)
    {
        int size = board[0].length;
        for(int i = 0; i < size; i++)
        {
            for(int a = 0; a < 5*size; a++)
            {
                System.out.print("-");
            }
            System.out.println();
            for(int j = 0; j < size; j++)
            {
                if(board[i][j] == 1)
                {
                    System.out.print("| X |");
                }
                else if(board[i][j] == 2)
                {
                    System.out.print("| O |");
                }
                else
                {
                    System.out.print("|   |");
                }
            }
            System.out.println();
        }
        for(int a = 0; a < 5*size; a++)
            {
                System.out.print("-");
            }
        System.out.println("\n\n");
    }
}