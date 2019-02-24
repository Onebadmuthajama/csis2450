package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Stack<Board> solutionBoard;
	int moves;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial.isSolvable() == false) {
			throw new java.lang.IllegalArgumentException();
		}
		solutionBoard = new Stack<>();

		MinPQ<Node> minPQ = new MinPQ<>();
		minPQ.insert(new Node(initial, 0, null));
		while (true) {
			Node node = minPQ.delMin();
			if (node.board.isGoal()) {
				this.moves = node.moves;
				do {
					solutionBoard.push(node.board);
					node = node.parent;
				} while (node != null);
				return;
			}
			for (Board next : node.board.neighbors()) {
				if (node.parent == null || !next.equals(node.parent.board)) {

					minPQ.insert(new Node(next, node.moves + 1, node));
				}
			}
		}
	}

	// Create a node class which will be the object in our stack.
	private class Node implements Comparable<Node> {
		private Board board;
		private int moves;
		private Node parent;

		public Node(Board b, int m, Node p) {
			this.board = b;
			this.moves = m;
			this.parent = p;
		}

		@Override
		public int compareTo(Node arg0) {
			int difference = this.board.manhattan() + this.moves - arg0.board.manhattan() - arg0.moves;
			if (difference != 0) {
				return difference;
			}
			if (this.moves > arg0.moves) {
				return -1;
			}
			return 1;
		}
	}

	// min number of moves to solve initial board
	public int moves() {
		return moves;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		return solutionBoard;
	}

	// solve a slider puzzle (given below) (test)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In("C:\\Users\\kaden\\Desktop\\puzzle00.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);
		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}
	}
}
