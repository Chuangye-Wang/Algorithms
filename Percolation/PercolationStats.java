import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import Percolation;
// import static java.lang.Math.sqrt;

public class PercolationStats {
	// perform independent trials on an n-by-n grid
	private double[] percolResult;
	private int n;
	private int trials;

	public PercolationStats(int n1, int trials1) {
		if (n1 <= 0 || trials1 <= 0)
		{
			throw new IllegalArgumentException("n cannot be less than 2");
		}
		n = n1;
		trials = trials1;
		percolResult = new double[trials];
		for (int k = 0; k < trials; k++) {
			// int count = 0;
			Percolation perloc = new Percolation(n);
			while (!perloc.percolates()) {
				int randi = StdRandom.uniform(n) + 1;
				int randj = StdRandom.uniform(n) + 1;
				// randi++;
				// randj++;
				while (perloc.isOpen(randi, randj)) {
					randi = StdRandom.uniform(n) + 1;
					randj = StdRandom.uniform(n) + 1;
					// randi++;
					// randj++;
					// System.out.println("-------3");
				}
				// count++;
				perloc.open(randi, randj);
			}
			// System.out.println("count = " + count);
			percolResult[k] = (double) perloc.numberOfOpenSites() / n / n;
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(percolResult);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(percolResult);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		double s = stddev();
		double mean = mean();
		double clow = mean - 1.96 * s / Math.sqrt(trials);
		return clow;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		double s = stddev();
		double mean = mean();
		double chigh = mean + 1.96 * s / Math.sqrt(trials);
		return chigh;
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int ts = Integer.parseInt(args[1]);
		PercolationStats pS = new PercolationStats(n, ts);
		double stdDev = pS.stddev();
		double mean = pS.mean();
		double clow = pS.confidenceLo();
		double chigh = pS.confidenceHi();
		System.out.println("mean = " + mean);
		System.out.println("stddev = " + stdDev);
		System.out.println("95% confidence interval = " + "[" + clow + ", " + chigh + "]");
		// System.out.println("trials = " + pS.trials);
	}
}