package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @authors Kaden Lovell, Wade Lewis, Brenden Andelin
 *
 *          PercolationStats get the average percolation percentage across T
 *          tests of size N*N, then returns the mean, standard deviation,
 *          confidence low, and confidence high.
 */
public class PercolationStats {
	public static void main(String[] args) {
		Percolation p = new Percolation(20);
		PercolationStats ps = new PercolationStats(20, 500);
	}

	int n;
	int t;
	double[] avgs;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = N;
		this.t = T;
		avgs = doMath(N, T);
		System.out.println(" mean: " + mean());
		System.out.println(" stddev: " + stddev());
		System.out.println(" confidenceLow: " + confidenceLow());
		System.out.println(" confidenceHigh: " + confidenceHigh());
	}

	/**
	 * 
	 * @param N
	 *            the side value of our square grid.
	 * @param T
	 *            the number of times to test the percolation percentage
	 * @return An array of percolation percentages.
	 */
	private double[] doMath(int N, int T) {
		double[] count = new double[T];
		for (int i = 0; i < t; i++) {
			Percolation p = new Percolation(N);
			int countOpenings = 0;

			while (!p.percolates()) {
				int row = StdRandom.uniform(N);
				int column = StdRandom.uniform(N);
				if (!p.isOpen(row, column)) {
					p.open(row, column);
					countOpenings++;
				}
			}
			count[i] = ((double) countOpenings / (N * N));
		}
		return count;
	}

	public double mean() {
		return StdStats.mean(avgs);
	}

	public double stddev() {
		return StdStats.stddev(avgs);
	}

	public double confidenceLow() {
		return mean() - StdStats.stddev(avgs);
	}

	public double confidenceHigh() {
		return mean() + StdStats.stddev(avgs);
	}
}
