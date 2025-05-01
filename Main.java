public class Main
{
    public static void main(String[] args)
    {
        TTTGames game = new TTTGames();
        double[] w1 = {1,-1,1,-1,1,-1};
        double[] w2 = {-1,1,-1,1,-1,1};
        double[] w = game.train(4, w1, w2, 5);
       
         for(int i = 0; i < w.length; i++)
        {
            System.out.print(w[i] + ", ");
        }
    }
}
