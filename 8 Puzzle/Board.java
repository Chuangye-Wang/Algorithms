/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int manhattanDis;
    private int blankRow, blankCol;
    private final int rowRandom;
    private final int colRandom;
    // private Board twinBoard;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        // this.tiles = tiles;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

        int row, col;
        int manhattanD = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                row = rowOfGoalTile(tiles[i][j] - 1);
                col = columnOfGoalTile(tiles[i][j] - 1);
                manhattanD += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        manhattanDis = manhattanD;

        int rowRandom1, colRandom1;
        rowRandom1 = StdRandom.uniform(n);
        colRandom1 = StdRandom.uniform(n);
        while (tiles[rowRandom1][colRandom1] == 0) {
            rowRandom1 = StdRandom.uniform(n);
            colRandom1 = StdRandom.uniform(n);
        }
        rowRandom = rowRandom1;
        colRandom = colRandom1;
    }

    // string representation of this board
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                output.append(String.format("%d ", tiles[i][j]));
            }
            output.append("\n");
        }
        return output.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDis = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != (i * n + j + 1)) hammingDis++;
            }
        }
        return hammingDis;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDis;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.deepEquals(this.tiles, that.tiles)) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsList = new ArrayList<Board>();
        int[][] neighborTiles;
        if (blankRow != 0) {
            neighborTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            swapUp(neighborTiles, blankRow, blankCol);
            Board neighborBoard = new Board(neighborTiles);
            neighborsList.add(neighborBoard);
        }
        if (blankRow != n-1) {
            neighborTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            swapDown(neighborTiles, blankRow, blankCol);
            Board neighborBoard = new Board(neighborTiles);
            neighborsList.add(neighborBoard);
        }
        if (blankCol != 0) {
            neighborTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            swapLeft(neighborTiles, blankRow, blankCol);
            Board neighborBoard = new Board(neighborTiles);
            neighborsList.add(neighborBoard);
        }
        if (blankCol != n-1) {
            neighborTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
            swapRight(neighborTiles, blankRow, blankCol);
            Board neighborBoard = new Board(neighborTiles);
            neighborsList.add(neighborBoard);
        }
        return neighborsList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);

        if (rowRandom != 0 && newTiles[rowRandom - 1][colRandom] != 0) {
            swapUp(newTiles, rowRandom, colRandom);
            // success = true;
        }
        else if (rowRandom != n - 1 && newTiles[rowRandom + 1][colRandom] != 0) {
            swapDown(newTiles, rowRandom, colRandom);
            // success = true;
        }
        else if (colRandom != 0 && newTiles[rowRandom][colRandom - 1] != 0) {
            swapLeft(newTiles, rowRandom, colRandom);
            // success = true;
        }
        else if (colRandom != n - 1 && newTiles[rowRandom][colRandom + 1] != 0) {
            swapRight(newTiles, rowRandom, colRandom);
            // success = true;
        }
        Board newBoard = new Board(newTiles);
        return newBoard;
    }

    private void swapUp(int[][] tilesArray, int row, int col) {
        int rowUp = row - 1;
        int temp;
        temp = tilesArray[row][col];
        tilesArray[row][col] = tilesArray[rowUp][col];
        tilesArray[rowUp][col] = temp;
    }

    private void swapDown(int[][] tilesArray, int row, int col) {
        int rowDown = row + 1;
        int temp;
        temp = tilesArray[row][col];
        tilesArray[row][col] = tilesArray[rowDown][col];
        tilesArray[rowDown][col] = temp;
    }

    private void swapLeft(int[][] tilesArray, int row, int col) {
        int colLeft = col - 1;
        int temp;
        temp = tilesArray[row][col];
        tilesArray[row][col] = tilesArray[row][colLeft];
        tilesArray[row][colLeft] = temp;
    }

    private void swapRight(int[][] tilesArray, int row, int col) {
        int colRight = col + 1;
        int temp;
        temp = tilesArray[row][col];
        tilesArray[row][col] = tilesArray[row][colRight];
        tilesArray[row][colRight] = temp;
    }

    private int rowOfGoalTile(int pos) {
        int c = pos/n;
        return c;
    }

    private int columnOfGoalTile(int pos) {
        return pos - rowOfGoalTile(pos) * n;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board initialCopy = new Board(tiles);
        Iterable<Board> neighborBoards;
        // ArrayList<Integer> a = new ArrayList<Integer>();
        // a.add(4);
        // boolean equal1 = initial == null;
        // boolean equal2 = initial.equals(a);
        boolean equal3 = initial.equals(initialCopy);

        System.out.println(initial.toString());
        System.out.println("if equal: " + equal3);

        neighborBoards = initial.neighbors();
        System.out.println("neighbor bod:" + neighborBoards);

        Board newBod = initial.twin();
        System.out.println("newBod 1 : " + newBod);
        newBod = initial.twin();
        System.out.println("newBod 2: " + newBod);
        newBod = initial.twin();
        System.out.println("newBod 3: " + newBod);
        newBod = initial.twin();
        System.out.println("newBod 4: " + newBod);
        newBod = initial.twin();
        System.out.println("newBod 5: " + newBod);
        System.out.println("initial: " + initial);

        System.out.println("initial Manht Dis: " + initial.manhattan());
        System.out.println("newBod Manht Dis: " + newBod.manhattan());
        // StdRandom.setSeed(1000);
        // int b = StdRandom.uniform(5);
        // int c = StdRandom.uniform(5);
        // System.out.println("b, c = " + b + " " + c);
    }
}
