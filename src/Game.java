public class Game {
    Board initialBoard;
    Board solvedBoard;

    int Nodes;
    int Backtracks;

    public Game(Board initialBoard) {
        this.initialBoard = initialBoard;
//        initialBoard.printMatrix();
//        initialBoard.printDomain();
    }

    void solve(Board board,int heuristics,Boolean forwardChecking) {
        //get time before solving
        long time = System.currentTimeMillis();
        Nodes=0;
        Backtracks=0;
        if(backtrack(board,heuristics,forwardChecking)){
            //return solvedBoard;
            long time2 = System.currentTimeMillis();
            System.out.println("Solved in "+(time2-time)+" ms");
            System.out.println("Nodes: "+Nodes);
            System.out.println("Backtracks: "+Backtracks);
            solvedBoard.printMatrix();
        }
        else {
            //return null;
            System.out.println("No solution");
        }
    }

    boolean backtrack(Board board,int heuristics,Boolean forwardChecking) {
        if (board.isComplete()) {
            solvedBoard = board;
            return true;
        }
        int[] next = board.next(heuristics);
        int row = next[0];
        int column = next[1];

        for (int i = 0; i < board.domain[row * board.n + column].size(); i++) {
            int value = (int) board.domain[row * board.n + column].get(i);
            Board newBoard=board.copy();
            newBoard.matrix[row][column] = value;
            newBoard.domainUpdate(row, column, value);
//            if(Nodes>=0){
//                System.out.println("Node: "+Nodes+"\t-> "+row+" "+column+"\tvalue:"+value+"\n");
//                //newBoard.printMatrix();
//            }
            Nodes++;

            if (backtrack(newBoard,heuristics,forwardChecking)) {
                return true;
            }
            //failedNodes++;
        }
        Backtracks++;
        return false;
    }
}
