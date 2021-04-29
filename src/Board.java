import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Stack;

public final class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] blocks) {
        int z = blocks.length;
        int[][] tiles = new int[z][z];
        for (int v = 0; v < z; v++) {
            System.arraycopy(blocks[v], 0, tiles[v], 0, z);
        }
        this.tiles = tiles;
        n = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int v = 0; v < n; v++) {
                s.append(" ").append(tiles[i][v]);
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlace = 0;
        int rightNum = 1;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n-1 & col == n-1) break;
                if (tiles[row][col] != rightNum) outOfPlace++;
                rightNum++;
            }
        }
        return outOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistance = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col]-1;
                if (value+1 == 0) {
                    continue;
                }
                int away = Math.abs((value/n) - row);
                int home = Math.abs((value%n) -col);
                int distance = home+away;
                totalDistance = totalDistance + distance;
            }
        }
        return totalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y==null) return false;
        if (y.getClass() != this.getClass()) return false;
        final Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int rowBlank = 0;
        int colBlank = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    rowBlank = i;
                    colBlank = j;
                }
            }
        }

        if (rowBlank > 0) {
            neighbors.push(exch(rowBlank,colBlank,rowBlank-1,colBlank));
        }
        if (rowBlank < n-1) {
            neighbors.push(exch(rowBlank,colBlank,rowBlank+1,colBlank));
        }
        if (colBlank > 0) {
            neighbors.push(exch(rowBlank,colBlank,rowBlank,colBlank-1));
        }
        if (colBlank < n-1) {
            neighbors.push(exch(rowBlank,colBlank,rowBlank,colBlank+1));
        }


        return neighbors;
    }

    private Board exch(int row1, int col1, int row2, int col2) {
        int[][] copy = new int[n][n];
        for (int v = 0; v < n; v++) {
            System.arraycopy(tiles[v], 0, copy[v], 0, n);
        }
        int oldVal = copy[row1][col1];
        int newVal = copy[row2][col2];
        copy[row1][col1] = newVal;
        copy[row2][col2] = oldVal;
        return new Board(copy);

    }

    public int tileAt(int row, int col) {
        if (row < 0 || row > n - 1) throw new IndexOutOfBoundsException
                ("row should be between 0 and N - 1");
        if (col < 0 || col > n - 1) throw new IndexOutOfBoundsException
                ("col should be between 0 and N - 1");

        return tiles[row][col];
    }

    public Boolean isSolvable() {
        return true;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        if (tiles[0][0] == 0 || tiles[0][1] == 0) {
            return exch(1,0,1,1);
        }
        return exch(0,0,0,1);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][] {
                {4, 8, 6},
                {2, 3, 0},
                {1, 5, 7}
        };
        int[][] tiles1 = new int[][] {
                {0,3},
                {2,1}
        };
        Board board = new Board(tiles);
        StdOut.println(board.twin());
        for (Board boar : board.neighbors()) {
            StdOut.println(boar.toString());
        }
    }
}
