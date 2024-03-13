package telran.java51.book.service;

import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.model.Author;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto deleteBook(String isbn);
	
	BookDto updateBookTitle(String id, String title);
	
	Iterable<BookDto> findBooksByAuthor(String author);
	
	Iterable<BookDto> findBooksByPublisher(String publisher);
	
	Iterable<AuthorDto> findBookAuthors(String isbn);
	
	Iterable<String> findPublishersByAuthor(String author);

	BookDto deleteAuthor(String author);

}
