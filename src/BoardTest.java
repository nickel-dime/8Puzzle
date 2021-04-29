import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    int[][] tiles = new int[][] {
            {4, 5, 3},
            {0, 8, 2},
            {7, 6, 1}
    };
    int[][] tiles2 = new int[][] {
            {8, 1, 4},
            {3, 0, 2},
            {7, 6, 5}
    };
    Board board = new Board(tiles);
    Board board2 = new Board(tiles);

    @org.junit.jupiter.api.Test
    void testToString() {

        // Act
        String expectedString = "3\n"
                + " 8 1 3\n"
                + " 4 0 2\n"
                + " 7 6 5\n";
        String boardString = board.toString();

        // Assert
        assertEquals(expectedString, boardString);
    }

    @org.junit.jupiter.api.Test
    void dimension() {
        int dimension = board.dimension();
        assertEquals(dimension, 3);
    }

    @org.junit.jupiter.api.Test
    void hamming() {
        assertEquals(board.hamming(), 5);
    }

    @org.junit.jupiter.api.Test
    void manhattan() {
        assertEquals(board.manhattan(), 10);
    }

    @org.junit.jupiter.api.Test
    void isGoal() {
        assertEquals(board.isGoal(), false);
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assertEquals(board.equals(board2), true);
    }

    @org.junit.jupiter.api.Test
    void neighbors() {
        assertEquals(board.neighbors(), 3);
    }

    @org.junit.jupiter.api.Test
    void twin() {
    }

}
