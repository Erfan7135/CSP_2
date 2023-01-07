import java.io.*;

public class Main {
    public static void main(String[] args) {
       int n;
       //scan from txt file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("d-10-01.txt"));
            n=Integer.parseInt(reader.readLine());
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] line = reader.readLine().trim().split("\\s*,\\s*");
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
            Board board = new Board(n);
            board.setMatrix(matrix);
            Game game = new Game(board);
            game.solve(board,2,false);
            //board.printMatrix();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}