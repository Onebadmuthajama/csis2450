package books;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// CSIS 2420, Algorithms and Data Structures, SLCC
// Author: Kaden Lovell
// Date: 9/1/2018
public class Book implements Comparable<Book> {
	private String title;
	private String author;
	private int year;

	// constructor
	public Book(String title, String author, int year) {
		this.title = title;
		this.author = author;
		this.year = year;
	}

	// title getter
	public String getTitle() {
		return title;
	}

	// author getter
	public String getAuthor() {
		return author;
	}

	// year getter
	public int getYear() {
		return year;
	}

	// list getter
	public static List<Book> getList(String f) {
		// new up list of arrays
		List<Book> books = new ArrayList<>();
		// new up a file, and pass parameter f into it
		File file = new File(f);
		// new up int and set it to 0, this keeps track of the line number that is being
		// read
		int lineCount = 0;
		// try catch to handle errors
		try {
			// new up a scanner, and pass file into it
			Scanner lines = new Scanner(file);
			// while scanner still has lines to read, create a book built from the parsed
			// data, add book to books, and increment lineCount.
			// if the data can't be parsed, return an error that tells the console what line
			// # couldn't be parsed, and displays that line number.
			// return a different message if the file can't be found, and have a generic
			// error catch to just to make sure that everything is being caught.
			while (lines.hasNext()) {
				String[] line = lines.nextLine().split(",");
				if (line.length == 3) {
					Book book = new Book(line[0], line[1], Integer.parseInt(line[2]));
					books.add(book);
					lineCount++;
				} else {
					System.out.println("There was a problem reading line number " + lineCount + ": "
							+ Arrays.toString(line).replace("[", "").replace("]", ""));
				}
			}
			lines.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("File was not found.");
		} catch (Exception e) {
			System.out.println("Something unexpected happened!." + lineCount);
		}

		return books;
	}

	// returns the data in a format that fits the criteria of the assignment.
	@Override
	public String toString() {
		return title + " By " + author + "( " + year + " )";
	}

	// Compares the current books title to the title being passed in.
	@Override
	public int compareTo(Book o) {
		return this.getTitle().compareTo(o.getTitle());
	}
}
