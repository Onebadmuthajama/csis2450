package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;

public class WordNet {

	SeparateChainingHashST<String, Queue<Integer>> wordToIntMap;
	SeparateChainingHashST<Integer, String> intToWordMap;
	Digraph digraph;
	SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new java.lang.NullPointerException();
		wordToIntMap = new SeparateChainingHashST<>();
		intToWordMap = new SeparateChainingHashST<>();
		int vertices = 0;
		In in = new In(synsets);

		while (in.hasNextLine()) {
			vertices++;
			String[] line = in.readLine().split(",");
			String[] words = line[1].split(" ");
			Integer number = Integer.valueOf(line[0]);
			intToWordMap.put(Integer.valueOf(line[0]), line[1]);

			for (int i = 0; i < words.length; i++) {
				Queue<Integer> wordToIntMapQ = wordToIntMap.get(words[i]);
				if (wordToIntMapQ == null) {
					wordToIntMapQ = new Queue<>();
					wordToIntMapQ.enqueue(number);
					wordToIntMap.put(words[i], wordToIntMapQ);
				} else {
					if (!contains(wordToIntMapQ, number)) {
						wordToIntMapQ.enqueue(number);
					}
				}
			}
		}

		digraph = new Digraph(vertices);
		in = new In(hypernyms);
		while (in.hasNextLine()) {
			String[] line = in.readLine().split(",");
			for (int i = 1; i < line.length; i++)
				digraph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
		}

		sap = new SAP(digraph);
		if (!sap.isRootedDAG())
			throw new java.lang.IllegalArgumentException("hypernyms not rooted DAG");

	}

	private <Item> boolean contains(Iterable<Item> iterable, Item item) {
		for (Item query : iterable) {
			if (query == item)
				return true;

		}
		return false;
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return wordToIntMap.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new java.lang.NullPointerException();
		return wordToIntMap.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new java.lang.NullPointerException();
		if (wordToIntMap.get(nounA) == null || wordToIntMap.get(nounB) == null)
			throw new java.lang.NullPointerException();

		Iterable<Integer> integerA = wordToIntMap.get(nounA);
		Iterable<Integer> integerB = wordToIntMap.get(nounB);
		return sap.length(integerA, integerB);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new java.lang.NullPointerException();
		if (wordToIntMap.get(nounA) == null || wordToIntMap.get(nounB) == null)
			throw new java.lang.NullPointerException();
		Iterable<Integer> integerA = wordToIntMap.get(nounA);
		Iterable<Integer> integerB = wordToIntMap.get(nounB);
		return intToWordMap.get(sap.ancestor(integerA, integerB));

	}

	// do unit testing of this class
	public static void main(String[] args) {

	}
}