/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private final Board initial;
    private final Board goal;
    private final int n;
    private final SearchNode minPQ;

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final int moves;
        private final Board board;
        private final SearchNode previousNode;

        public SearchNode(Board bd, int mvs, SearchNode preNode) {
            this.board = bd;
            this.moves = mvs;
            this.previousNode = preNode;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("input board is null");
        }
        this.initial = initial;
        n = initial.dimension();
        // Create goal Board;
        goal = createGoal();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();

        SearchNode pqMin, pqTwinMin;
        pq.insert(new SearchNode(initial, 0, null));
        // System.out.println("pq size " + pq.size());
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        // System.out.println("pqTwin size " + pqTwin.min().board);
        // int i = 0;
        while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal()) {
            pqMin = pq.min();
            pqTwinMin = pqTwin.min();
            pq.delMin();
            pqTwin.delMin();

            for (Board neighbor: pqMin.board.neighbors()) {
                if (pqMin.moves == 0) {
                    pq.insert(new SearchNode(neighbor, 1, pqMin));
                }
                else if (!neighbor.equals(pqMin.previousNode.board)) {
                    pq.insert(new SearchNode(neighbor, pqMin.moves + 1, pqMin));
                }
            }

            for (Board neighbor: pqTwinMin.board.neighbors()) {
                if (pqTwinMin.moves == 0) {
                    pqTwin.insert(new SearchNode(neighbor, 1, pqTwinMin));
                }
                else if (!neighbor.equals(pqTwinMin.previousNode.board)) {
                    pqTwin.insert(new SearchNode(neighbor, pqTwinMin.moves + 1, pqTwinMin));
                }
                // System.out.println("for pqTwin size " + pqTwin.size());
            }
        }
        minPQ = pq.min();
        // System.out.println("i = " + i);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (minPQ.board.isGoal()) {
            return true;
        }
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        else return minPQ.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        ArrayList<Board> solutionList = new ArrayList<Board>();
        SearchNode sNode = minPQ;
        while (sNode.previousNode != null) {
            solutionList.add(sNode.board);
            sNode = sNode.previousNode;
        }
        solutionList.add(initial);
        Collections.reverse(solutionList);
        return solutionList;
    }

    private Board createGoal() {
        int[][] goalTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalTiles[i][j] = i*n + j + 1;
            }
        }
        goalTiles[n-1][n-1] = 0;
        return new Board(goalTiles);
    }
    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.print(initial);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        System.out.println(solver.goal);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
