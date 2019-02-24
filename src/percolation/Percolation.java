package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @authors Kaden Lovell, Wade Lewis, Brenden Andelin
 * Creates a testing model for the Monte Carolo simulation.
 */
public class Percolation {
	private int n;
	private int size;
	private boolean[][] grid;
	private WeightedQuickUnionUF unionFind;
	int numberOpen = 0;
/**
 * Constructor for the Percolation object, Creates an N by N grid.
 * @param N The length of a side on the square grid.
 */
	public Percolation(int N) {
		if (n < 0) {
			return;
		}
		this.n = N;
		size = n * n;
		grid = new boolean[n][n];
		unionFind = new WeightedQuickUnionUF(size + 2);
	}
	
	/**
	 * Opens a location in the grid.
	 * @param i x coordinate
	 * @param j y coordinate 
	 */
	public void open(int i, int j) {
		validator(i, j);

		grid[i][j] = true;

		if (i - 1 >= 0 && grid[i - 1][j] || i - 1 == -1) {
			if (i - 1 == -1) {
				unionFind.union(n, location(i, j));
			} else {
				unionFind.union(location(i - 1, j), location(i, j));
			}
		}
		if (i + 1 < n && grid[i + 1][j] || i + 1 == n) {
			if (i + 1 == n) {
				unionFind.union(n + 1, location(i, j));
			} else {
				unionFind.union(location(i + 1, j), location(i, j));
			}
		}
		if (j - 1 >= 0 && grid[i][j - 1]) {
			unionFind.union(location(i, j - 1), location(i, j));
		}
		if (j + 1 < n && grid[i][j + 1]) {
			unionFind.union(location(i, j + 1), location(i, j));
		}

		numberOpen++;
	}

	public boolean isOpen(int i, int j) {
		validator(i, j);
		return grid[i][j];
	}

	public boolean isFull(int i, int j) {
		validator(i, j);
		if (!isOpen(i, j)) {
			return false;
		}
		return unionFind.connected(n, location(i, j));
	}

	public boolean percolates() {
		return unionFind.connected(n, n + 1);
	}
	
	/**
	 * 
	 * @param i x coordinate
	 * @param j y coordinate
	 * @return returns the index that equals the (x,y) coordinate on a one dimensional array.
	 */
	private int location(int i, int j) {
		return (n * j) + i;
	}

	/**
	 * Makes sure that params are in-bounds of the grid. Method was created to clean up the code.
	 * @param i x coordinate
	 * @param j y coordinate
	 */
	private void validator(int i, int j) {
		if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
			throw new IndexOutOfBoundsException(
					"row index " + i + " and column index " + j + " must be between 0 and " + (n - 1));
		}
	}

	/**
	 * Created entirely for the PercolationVisualizer class.
	 * @return
	 */
	public String numberOfOpenSites() {
		return "" + numberOpen;
	}
}