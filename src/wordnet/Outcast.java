package wordnet;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.ST;

public class Outcast {

	private WordNet wordnet;

	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		this.wordnet = wordnet;

	}

	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast

		ST<Integer, String> st = new ST<>();
		MaxPQ<Integer> maxPQ = new MaxPQ<>();

		for (int i = 0; i < nouns.length; i++) {
			int distance = 0;
			for (int j = 0; j < nouns.length; j++) {
				distance += wordnet.distance(nouns[i], nouns[j]);
			}

			maxPQ.insert(distance);
			st.put(distance, nouns[i]);
		}

		return st.get(maxPQ.max());

	}

	public static void main(String[] args) { // see test client below

	}
}