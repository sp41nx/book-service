package telran.java51.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.dto.BookDto;
import telran.java51.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	

}
