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
                w1[i] = Math.pow(2,i/2);
                w2[i] = Math.pow(2,1/2) * -1;
            }
            else
            {
                w1[i] = Math.pow(2,i/2) * -1;
                w2[i] = Math.pow(2,i/2);
            }
        }
        System.out.println("Would you like to\n1. Play a Game\n2. Watch a game");
        int c = scan.nextInt();
        int games = 100000;
        if(b >= 5 && b <= 7)
            games = 10000;
        if(b > 7)
            games = 1000;
        double[] w = game.train(b, w1, w2, 5, games);
        System.out.println("AI Weights");
        for(int i = 0; i < w.length; i++)
        {
            System.out.print(w[i] + ", ");
        }
        if(c == 1)
        {
            System.out.println("\nReady to play a Game");
            int d = game.playHuman(b,w);
            if(d == 1)
                System.out.println("You Win");
            else if (d==2)
                System.out.println("You Lost");
            else
                System.out.println("You Tied");
            scan.close();
        }
        else if(c == 2)
        {
            double[] weights2 = game.train(b, w1, w2, 5, games);
            System.out.println("\nAI 2 Weights");
            for(int i = 0; i < w.length; i++)
            {
                System.out.print(w[i] + ", ");
            }
            int e = game.playGame(b, w, weights2, true);
            if(e == 1)
                System.out.println("Player 1 Wins");
            else if (e==2)
                System.out.println("Player 2 Wins");
            else
                System.out.println("Its a Tie");

        }
    }
}

