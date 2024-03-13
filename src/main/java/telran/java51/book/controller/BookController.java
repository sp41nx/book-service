package telran.java51.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.model.Author;
import telran.java51.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {
	final BookService bookService;
	
	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}
	
	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}
	
	@DeleteMapping("/book/{isbn}")
	public BookDto deleteBook(@PathVariable String isbn) {
		return bookService.deleteBook(isbn);
	}
	
	@PutMapping("/book/{id}/title/{title}")
	public BookDto updateBookTitle(@PathVariable String id, @PathVariable String title) {
		return bookService.updateBookTitle(id, title);
	}
	
	@GetMapping("/books/author/{author}")
	public Iterable<BookDto> findBooksByAuthor(@PathVariable String author) {
		return bookService.findBooksByAuthor(author);
	}
	
	@GetMapping("/books/publisher/{publisher}")
	public Iterable<BookDto> findBooksByPublisher(@PathVariable String publisher) {
		return bookService.findBooksByPublisher(publisher);
	}
	
	@GetMapping("/authors/book/{isbn}")
	public Iterable<AuthorDto> findBookAuthors(@PathVariable String isbn) {
		return bookService.findBookAuthors(isbn);
	}
	
	@GetMapping("/publishers/author/{author}")
	public Iterable<String> findPublishersByAuthor(@PathVariable String author) {
		return bookService.findPublishersByAuthor(author);
	}
	
	@DeleteMapping("/author/{author}")
	public BookDto deleteAuthor(@PathVariable String author) {
		return bookService.deleteAuthor(author);
	}
}
