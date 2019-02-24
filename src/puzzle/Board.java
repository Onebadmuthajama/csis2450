package puzzle;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Brended Andolin
 * @author Kaden Lovell
 *
 */
public class Board {
	private final int N;
	private final int[] tiles;
	private int hamming = 0;
	private int zeroIndex = 0;
	private int manhattan = 0;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		if (blocks == null) {
			throw new java.lang.NullPointerException();
		}
		N = blocks.length;

		tiles = new int[N * N];
		int initialTile = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0) {
					zeroIndex = initialTile;
				}
				tiles[initialTile++] = blocks[i][j];
			}
		}
	}

	// board size N
	public int size() {
		return N;
	}

	// number of blocks out of place
	public int hamming() {
		if (hamming != 0) {
			return hamming;
		}

		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] != (i + 1) && tiles[i] != 0) {
				hamming++;
			}
		}
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		if (manhattan != 0) {
			return manhattan;
		}

		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == (i + 1) || i == zeroIndex) {
				continue;
			}
			manhattan += Math.abs((i / N) - ((tiles[i] - 1)) / N);
			manhattan += Math.abs((i % N) - ((tiles[i] - 1)) % N);
		}
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		if (tiles[tiles.length - 1] != 0) {
			return false;
		}
		for (int i = 0; i < tiles.length - 1; i++) {
			if (tiles[i] != (i + 1)) {
				return false;
			}
		}
		return true;
	}

	// is this board solvable
	public boolean isSolvable() {
		int inversions = 0;
		// Don't count blank inversions
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == 0) {
				continue;
			}
			// check to see if there are larger numbers left after i
			for (int j = i; j < tiles.length; j++) {
				if (tiles[j] < tiles[i] && tiles[j] != 0) {
					inversions++;
				}
			}
		}

		// true if even, false if odd
		// if the board is even, add which row 0 is on
		boolean isEvenBoard = (N % 2) == 0;
		if (isEvenBoard) {
			inversions += zeroIndex / N;
		}

		// true if inversions is even
		// an odd board can be solved with even inversions.
		// an even board can solved with odd inversions.
		// return true if the values are not both equal.
		boolean isEvenInversions = (inversions % 2) == 0;
		return (isEvenBoard != isEvenInversions);
	}

	// does this board equal object y
	@Override
	public boolean equals(Object y) {
		if (y == this) {
			return true;
		}

		if (y == null) {
			return false;
		}
		// use reflection to get the class of y, and compare it to this class to ensure
		// they are the same type.
		if (this.getClass() != y.getClass()) {
			return false;
		}
		// this cast is a 100% safe cast since y is of this class.
		Board x = (Board) y;
		// go through both boards and compare them index by index, return false if the
		// are not equal.
		for (int i = 0; i < tiles.length; i++) {
			if (this.tiles[i] != x.tiles[i]) {
				return false;
			}
		}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		Stack<Board> neighborBords = new Stack<>();

		if (zeroIndex / N != 0) {
			pushNeighborToStack(neighborBords, -N); // moved up neighbor board
		}
		if (zeroIndex / N != N - 1) {
			pushNeighborToStack(neighborBords, N); // moved down neighbor board
		}
		if (zeroIndex % N != 0) {
			pushNeighborToStack(neighborBords, -1); // moved left neighbor board
		}
		if (zeroIndex % N != N - 1) {
			pushNeighborToStack(neighborBords, 1); // moved right neighbor board
		}
		return neighborBords;
	}

	private void pushNeighborToStack(Stack<Board> neighbors, int i) {
		swap(tiles, zeroIndex, zeroIndex + i);
		neighbors.push(new Board(tiles, N, zeroIndex + i));
		swap(tiles, zeroIndex, zeroIndex + i);
	}

	private void swap(int[] board, int i, int j) {
		int swap = board[i];
		board[i] = board[j];
		board[j] = swap;
	}

	private Board(int[] blocks, int N, int zeroIndex) {
		this.N = N;
		this.zeroIndex = zeroIndex;
		tiles = new int[N * N];
		System.arraycopy(blocks, 0, tiles, 0, tiles.length);
	}

	// a slightly modified version of the to-string method that was provided in the
	// assignment.
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < tiles.length; i++) {
			s.append(String.format("%2d ", tiles[i]));
			if ((i + 1) % N == 0)
				s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		Board testBoard = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
		StdOut.println(testBoard.toString());
	}
}