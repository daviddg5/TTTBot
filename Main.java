public class Main
{
    public static void main(String[] args)
    {
        TTTGames game = new TTTGames();
        BoardExtraction be = new BoardExtraction();
        //Game Board Size
        int size = 5;
        //Amount in a row to win
        int win = size/2 + 2;
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
       
        for(int i = 0; i < w.length; i++)
        {
            System.out.print(w[i] + ", ");
        }
    }
}
