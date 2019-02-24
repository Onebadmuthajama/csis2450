package wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Stack;

public class SAP {

	private Digraph graph;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new java.lang.NullPointerException();
		this.graph = new Digraph(G);

	}

	// is the digraph a directed acyclic graph?
	public boolean isDAG() {
		return !new DirectedCycle(graph).hasCycle();
	}

	// is the digraph a rooted DAG?
	public boolean isRootedDAG() {
		if (!isDAG())
			return false;

		DepthFirstOrder dfo = new DepthFirstOrder(this.graph);
		Integer root = dfo.post().iterator().next();

		DepthFirstDirectedPaths dfp = new DepthFirstDirectedPaths(graph.reverse(), root);
		for (int i = 0; i < graph.V(); i++) {
			if (!dfp.hasPathTo(i))
				return false;
		}
		return true;

	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		Stack<Integer> vStack = new Stack<>();
		vStack.push(v);
		Stack<Integer> wStack = new Stack<>();
		wStack.push(w);
		return ancestorLength(vStack, wStack)[1];
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		Stack<Integer> vStack = new Stack<>();
		vStack.push(v);
		Stack<Integer> wStack = new Stack<>();
		wStack.push(w);
		return ancestorLength(vStack, wStack)[0];
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		return ancestorLength(v, w)[1];
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return ancestorLength(v, w)[0];
	}

	private int[] ancestorLength(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(graph, w);

		DepthFirstOrder dfo = new DepthFirstOrder(graph);

		int ancestor = -1;
		int length = -1;

		for (int i : dfo.reversePost()) {
			if (vpath.hasPathTo(i) && wpath.hasPathTo(i)) {

				int currentLength = vpath.distTo(i) + wpath.distTo(i);

				if (currentLength < length || ancestor == -1) {
					ancestor = i;
					length = currentLength;
				} else
					break;
			}
		}

		int[] ancestorAndLength = { ancestor, length };
		return ancestorAndLength;
	}

	// do unit testing of this class
	public static void main(String[] args) {

	}
}