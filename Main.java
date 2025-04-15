public class Main
{
    public static void main(String[] args)
    {
        TTT game = new TTT(5);
        int [][] board = {{0,0,0,2,0}, {0,0,1,2,0}, {0,1,0,1,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        game.board = board;
        int[] features = game.countWins();
        for(int i = 0; i < features.length; i++)
        {
            System.out.print(features[i] + ",  ");
        }
    }
}
