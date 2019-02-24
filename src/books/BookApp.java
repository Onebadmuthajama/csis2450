package books;

import java.util.Collections;
import java.util.List;

public class BookApp {
	// CSIS 2420, Algorithms and Data Structures, SLCC
	// Author: Kaden Lovell
	// Date: 9/1/2018
	public static void main(String[] args) {
		System.out.println("Error List:");
		// gets the booklist that was created inside of the class book
		List<Book> books = Book.getList("src/books/books.csv");
		// line break to separate data out in a more user-friendly format
		System.out.println("");
		System.out.println("Sorted List:");
		// use Collections.sort to sort books, sort uses the compareTo override in the book class to compare the books and sort them.
		Collections.sort(books);
		// for every book in books print out book
		for (Book book : books) {
			System.out.println(book.toString());
		}
		// line break to separate data out in a more user-friendly format
		System.out.println("");
		// collections.sort to sort books, sort uses the compareTo override in the book class to compare the books and sort them. (sorts in a reversed order compared to previous sort)
		Collections.sort(books, Collections.reverseOrder());
		System.out.println("Reverse Order");
		// for every book in books print out book
		for (Book book : books) {
			System.out.println(book.toString());
		}
	}

}
