import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.Stack;

public class Solver {

    private SearchNode searchNode;
    private Stack<Board> solution = new Stack<>();
    private Boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board is null");
        SearchNode searchNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(searchNode);

        SearchNode searchNode1 = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> pq1 = new MinPQ<>();
        pq1.insert(searchNode1);

        while (true) {
            searchNode = pq.delMin();
            searchNode1 = pq1.delMin();

            if (searchNode.board.isGoal() || searchNode1.board.isGoal()) {
                break;
            }
            for (Board board: searchNode.board.neighbors()) {
                SearchNode x = new SearchNode(board, searchNode.moves + 1, searchNode);
                if (searchNode.prev == null || (!x.board.equals(searchNode.prev.board))) {
                    pq.insert(x);
                }
            }
            for (Board board: searchNode1.board.neighbors()) {
                SearchNode x = new SearchNode(board, searchNode1.moves + 1, searchNode1);
                if (searchNode1.prev == null || (!x.board.equals(searchNode1.prev.board))) {
                    pq1.insert(x);
                }
            }
        }


        this.searchNode = searchNode;
        if (searchNode.board.isGoal()) {
            solution.push(searchNode.board);
            while (searchNode.prev != null) {
                solution.push(searchNode.prev.board);
                searchNode = searchNode.prev;
            }
            solvable = true;
        } else {
            solvable = false;
        }

    }

    private static class SearchNode implements Comparable<SearchNode> {
        private int priority;
        private Board board;
        private int moves;
        private SearchNode prev;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.priority = moves + board.manhattan();
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.priority > o.priority) {
                return 1;
            } else if (this.priority == o.priority) {
                return 0;
            }
            return -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return searchNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Collections.reverse(solution);
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        int[][] tiles = new int[][] {
                {1, 7, 14, 4},
                {6, 3, 11, 0},
                {10, 13, 12, 8},
                {5,9,2,15}
        };
        int[][] tiles1 = new int[][] {
                {1,0},
                {2,3}
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        StdOut.println(solver.isSolvable());
        for (Board boar : solver.solution()) {
            StdOut.println(boar.toString());
        }
    }

}
