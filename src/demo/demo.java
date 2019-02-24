package demo;

import edu.princeton.cs.algs4.In;

public class demo {

	public static void main(String[] args) {
		In in = new In("src/synset.txt");
		String[] line = null;
		String[] words = null;
		while (in.hasNextLine()) {
			line = in.readLine().split(",");
			words = line[1].split(" ");

		}
		System.out.println(line[1]);
		System.out.println(words[2]);

	}
}
