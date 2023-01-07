import java.util.ArrayList;

public class Board {
    int n;
    int[][] matrix;
    ArrayList[] domain;

    public Board(int n) {
        this.n = n;
        this.matrix = new int[n][n];
        this.domain = new ArrayList[n * n];
    }

    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.domain = getDomain(matrix);
    }

    ArrayList<Integer>[] getDomain(int[][] matrix) {
        ArrayList<Integer>[] domain = new ArrayList[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    domain[i * n + j] = null;
                } else {
                    domain[i * n + j] = new ArrayList<>();
                    for (int k = 1; k <= n; k++) {
                        if (checkRow(i, k) && checkColumn(j, k)) {
                            domain[i * n + j].add(k);
                        }
                    }
                }
            }
        }
        return domain;
    }

    boolean checkRow(int row, int value) {
        for (int i = 0; i < n; i++) {
            if (matrix[row][i] == value) {
                return false;
            }
        }
        return true;
    }

    boolean checkColumn(int column, int value) {
        for (int i = 0; i < n; i++) {
            if (matrix[i][column] == value) {
                return false;
            }
        }
        return true;
    }


    void printMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(matrix[i][j]<10)
                    System.out.print(" ");
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }


    void printDomain() {
        System.out.println("Domain:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (domain[i * n + j] != null) {
                    System.out.println("Cell " + i + " " + j + ": ");
                    System.out.print(domain[i * n + j].size()+":");
                    for (int k = 0; k < domain[i * n + j].size(); k++) {
                        System.out.print(" " + domain[i * n + j].get(k));
                    }
                    System.out.println("\n");
                }
            }
            System.out.println();
        }
    }

    boolean isComplete() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    int[] next(int k) {
        if(k==1) {
            int[] curr = null;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        if(curr == null){
                            curr = new int[]{i, j};
                        }
                        else if(domain[i*n+j].size()<domain[curr[0]*n+curr[1]].size()){
                            curr = new int[]{i, j};
                        }
                    }
                }
            }
            return curr;
        }
        else if(k==2){
            int[] curr = null;
            int degree = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        if(curr == null){
                            curr = new int[]{i, j};
                            degree = getDegree(i, j);
                        }
                        else{
                            int degree1 = getDegree(i, j);
                            if(degree1>degree){
                                curr = new int[]{i, j};
                                degree = degree1;
                            }
                        }
                    }
                }
            }
            return curr;
        }
        else if (k==3){
            int[] curr = null;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        if(curr == null){
                            curr = new int[]{i, j};
                        }
                        else if(domain[i*n+j].size()<domain[curr[0]*n+curr[1]].size()){
                            curr = new int[]{i, j};
                        }
                        else if (domain[i * n + j].size() == domain[curr[0] * n + curr[1]].size()) {
                            int degree1 = getDegree(i, j);
                            int degree2 = getDegree(curr[0], curr[1]);
                            if (degree1 > degree2) {
                                curr = new int[]{i, j};
                            }
                        }
                    }
                }
            }
            return curr;
        }


        int[] next = new int[2];
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    next[0] = i;
                    next[1] = j;
                    list.add(next);
                }
            }
        }
        int random = (int) (Math.random() * 100)%(list.size());
        return list.get(random);
    }

    private int getDegree(int i, int j) {
        int degree=0;
        for (int k1 = 0; k1 < n; k1++) {
            if(matrix[i][k1]==0){
                degree++;
            }
            if(matrix[k1][j]==0){
                degree++;
            }
        }
        degree-=2;
        return degree;
    }

    void domainUpdate(int row, int column, int value) {
        for (int i = 0; i < n; i++) {
            if (domain[row * n + i] != null) {
                domain[row * n + i].remove((Integer) value);
            }
            if (domain[i * n + column] != null) {
                domain[i * n + column].remove((Integer) value);
            }
        }
        domain[row * n + column] = null;
    }

    public Board copy() {
        Board newBoard = new Board(n);
        newBoard.matrix = new int[n][n];
        newBoard.domain = new ArrayList[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard.matrix[i][j] = matrix[i][j];
                if (domain[i * n + j] == null) {
                    newBoard.domain[i * n + j] = null;
                } else {
                    newBoard.domain[i * n + j] = new ArrayList<>();
                    for (int k = 0; k < domain[i * n + j].size(); k++) {
                        newBoard.domain[i * n + j].add(domain[i * n + j].get(k));
                    }
                }
            }
        }
        return newBoard;
    }
}
