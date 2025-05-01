import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        TTTGames game = new TTTGames();
        BoardExtraction be = new BoardExtraction();
        Scanner scan = new Scanner(System.in);
        int b = 0;
        System.out.println("What board size would you like");
        b = scan.nextInt();
        int win = b/2 + 2;
        double[] w1 = new double[2*win];
        double[] w2 = new double[2*win];
        //Generate Initial Weights
        // w1 = 1,-1,1,-1...
        // w2 = -1,1,-1,1...
        for(int i = 0; i < (2 * win); i++)
        {
            if((i+2)%2==0)
            {
                w1[i] = 1;
                w2[i] = -1;
            }
            else
            {
                w1[i] = -1;
                w2[i] = 1;
            }
        }
        double[] w = game.train(5, w1, w2, 5);
        System.out.println("AI Weights");
        for(int i = 0; i < w.length; i++)
        {
            System.out.print(w[i] + ", ");
        }
        System.out.println("\nReady to play a Game");
        int c = game.playHuman(b,w);
        if(c == 1)
            System.out.println("You Win");
        else if (c==2)
            System.out.println("You Lost");
        else
            System.out.println("You Tied");
        scan.close();
    }
}

