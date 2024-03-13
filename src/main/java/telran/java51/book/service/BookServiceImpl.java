package telran.java51.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.hibernate.transform.ToListResultTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.exceptions.BookNotFoundException;
import telran.java51.book.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	
	final BookRepository bookRepository;
	final ModelMapper modelMapper;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;
    
	@Transactional
	@Override
	public boolean addBook(BookDto bookDto) {
		if(bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
//		//Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
				.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
				
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);		
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto deleteBook(String isbn) {
		Book book = bookRepository.findById(isbn)
							   	  .orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}
	
	@Transactional
	@Override
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn)
								  .orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public Iterable<BookDto> findBooksByAuthor(String author) {
		Author tmpAuthor = new Author();
		tmpAuthor.setName(author);
		return bookRepository.findAll()
							 .stream()
							 .filter(f -> f.getAuthors().contains(tmpAuthor))
							 .map(b -> modelMapper.map(b, BookDto.class))
							 .collect(Collectors.toList());
	
	}

	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisher) {
        return bookRepository.findAll()
        					 .stream()
        				     .filter(b -> b.getPublisher().toString().equalsIgnoreCase(publisher))
					         .map(b -> modelMapper.map(b, BookDto.class))
					         .collect(Collectors.toList());
	}

	@Override
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn)
								  .orElseThrow(EntityNotFoundException::new);
		return book.getAuthors()
				   .stream()
				   .map(a -> modelMapper.map(a, AuthorDto.class))
				   .collect(Collectors.toList());
	}

	@Override
	public Iterable<String> findPublishersByAuthor(String author) {
		Author tmpAuthor = new Author();
		tmpAuthor.setName(author);
		return bookRepository.findAll().stream()
							 .filter(b -> b.getAuthors().contains(tmpAuthor))
							 .map(b -> b.getPublisher().toString())
							 .collect(Collectors.toList());
	}

	@Override
	public BookDto deleteAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
