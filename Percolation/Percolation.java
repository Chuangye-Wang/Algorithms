// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import java.util.Arrays;

public class Percolation {
	// creates n-by-n grid, with all sites initially blocked
	// blocked sites: false
	private int top = 0;
	private int bottom;
	private boolean[][] grid; // grid is the perlocation matrix
	private WeightedQuickUnionUF wqf;
	private WeightedQuickUnionUF wqfFull;
	private int gridSize;
	
    public Percolation(int n)
    {
    	if (n <= 0)
		{
			throw new IllegalArgumentException("n cannot be less than 1");
		}
		gridSize = n;
		bottom = n*n + 1;
    	grid = new boolean[n][n];
    	wqf = new WeightedQuickUnionUF(n*n + 2);
    	wqfFull = new WeightedQuickUnionUF(n*n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
    	if (row <= 0 || row > gridSize) throw new IllegalArgumentException("row index out of bounds");
    	if (col <= 0 || col > gridSize) throw new IllegalArgumentException("column index out of bounds");
    	grid[row-1][col-1] = true;
    	if (row == 1) {
    		wqf.union(to2D(row, col), top);
			wqfFull.union(to2D(row, col), top);
		}
		if (row == gridSize) wqf.union(to2D(row, col), bottom);
    	if (row >= 2 && isOpen(row - 1, col))  // up
    	{
    		wqf.union(to2D(row, col), to2D(row-1, col));
			wqfFull.union(to2D(row, col), to2D(row-1, col));
    	}
    	if (row <= (gridSize - 1) && isOpen(row+1, col))  // down
    	{
    		wqf.union(to2D(row, col), to2D(row+1, col));
			wqfFull.union(to2D(row, col), to2D(row+1, col));
    	}
    	if (col >= 2 && isOpen(row, col-1))  // left
    	{
    		wqf.union(to2D(row, col), to2D(row, col-1));
			wqfFull.union(to2D(row, col), to2D(row, col-1));
    	}
    	if (col <= (gridSize - 1) && isOpen(row, col+1))  // right
    	{
    		wqf.union(to2D(row, col), to2D(row, col+1));
			wqfFull.union(to2D(row, col), to2D(row, col+1));
    	}
    }

    // is the site (row, column) open?
    public boolean isOpen(int row, int col)
    {
    	if (row <= 0 || row > gridSize) throw new IllegalArgumentException("row index out of bounds");
    	if (col <= 0 || col > gridSize) throw new IllegalArgumentException("column index out of bounds");
    	return grid[row-1][col-1];
    }
    // is the site (row, column) full?
    // @SuppressWarnings("deprecation")
	public boolean isFull(int row, int col)
    {
		if (row <= 0 || row > gridSize) throw new IllegalArgumentException("row index out of bounds");
    	if (col <= 0 || col > gridSize) throw new IllegalArgumentException("column index out of bounds");
    	// return wqfFull.connected(to2D(row, col), top);
		return wqfFull.find(to2D(row, col)) == wqfFull.find(top);
    }
    // returns the number of open sites
    public int numberOfOpenSites()
    {
    	int count = 0;
    	for (int i = 1; i <= gridSize; i++)
    		for (int j = 1; j <= gridSize; j++)
	    	{
	    		if (isOpen(i, j)) count++;
	    	}
	    return count;
    }
	public boolean percolates()
    {
    	// return wqf.connected(top, bottom);
		return wqf.find(top) == wqf.find(bottom);
    }
    // test client (optional)
    
    private int to2D(int row, int col)
    {
    	return (row - 1) * gridSize + col;
    }
    public static void main(String[] args)
    {
    	/*
    	int n = Integer.parseInt(args[0]);
    	System.out.println("gridSize =  " + n);
    	Percolation perloc = new Percolation(n);
    	int limit = 10*n*n;
    	int c = 0;
		// System.out.println("-------1");
    	while(!perloc.percolates()) {
    		int randi = StdRandom.uniform(n);
    		int randj = StdRandom.uniform(n);
    		randi++;
    		randj++;
    		if (!perloc.isOpen(randi, randj))
    		{
    			perloc.open(randi, randj);
				//System.out.println("-------3");
    		}
			//System.out.println("-------4");
    		c++;
			//System.out.println("c = " + c + "\n");
    		if (c > limit) 
    		{
    			System.out.println("too many opens, check the codes");
    			break;
    		}	
    	}
		System.out.println("c = " + c);
		System.out.println("grid = " + Arrays.deepToString(perloc.grid));
		System.out.println("toal number of open sites = " + perloc.numberOfOpenSites());
    	if (perloc.percolates()) System.out.println("Perlocation allowed, you finished great job");
        */
    }
}
