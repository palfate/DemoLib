package com.demo.library.application.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.library.application.dao.AuthorRepository;
import com.demo.library.application.dao.BookRepository;
import com.demo.library.application.domain.Author;
import com.demo.library.application.domain.Book;

import org.springframework.data.domain.Sort;


@RestController
@RequestMapping
public class LibraryController {

	@Autowired
	//LibraryService libraryService;
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	//List Books sorted by author or name (User) 
	@GetMapping(value = "/bookSortByTitle/{orderBy}")
	public List <Book> getAllBooksSortedByName(@PathVariable String orderBy) {
		if(orderBy.equalsIgnoreCase("ASC"))
			return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
		else
			return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "title"));
	}

	//List one book, find by id (user)
	@GetMapping("/book/{id}")
	public Optional<Book> getBook(@PathVariable String id) {
		int bookId = Integer.parseInt(id);
		return bookRepository.findById(bookId);

	}

	//List book based on title (user) - filter
	@GetMapping("/bookdetails/{title}")
	public List<Book> getBookdetail(@PathVariable String title) {

		List<Book> books = bookRepository.findAll();
		List<Book> filterBooks =  new ArrayList<Book>();
		for (Book book : books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				filterBooks.add(book);
			}
		}      
		return filterBooks;
	}

	//List all books (user)
	@GetMapping("/booklist")
	public List<Book> getBookList() {
		return bookRepository.findAll();
	}

	//Add single book (user)
	@PostMapping("/addBook") 
	public Book addBook(@RequestBody Map<String, String> body) {
		Author author = new Author(body.get("author"));
		Author createAuthor = authorRepository.save(author);
		Set<Author> authors = new HashSet<>();
		authors.add(createAuthor);
		Book book = new Book(body.get("title"));
		book.setAuthors(authors);

		return bookRepository.save(book);
	}

	//Add new books (user)
	@PostMapping("/addBooks") 
	public String addBooks(@RequestBody ArrayList<Map<String, String>>  body) {

		for (Map<String, String> map : body) {
			Author author = new Author(map.get("author"));
			Author createAuthor = authorRepository.save(author);
			Set<Author> authors = new HashSet<>();
			authors.add(createAuthor);
			Book book = new Book(map.get("title"));
			book.setAuthors(authors);
			bookRepository.save(book);
		}
		return "success";

	}

	//Update book title (user)
	@PutMapping("/updateBookTitle")
	public String updateBookName(@RequestBody Map<String, String> body) {

		int bookId = Integer.parseInt(body.get("id"));
		Optional<Book> optional = bookRepository.findById(bookId);
		Book bookObj = optional.get();
		bookObj.setId(bookId);
		String title = body.get("title");
		bookObj.setTitle(title);
		try {
			bookRepository.save(bookObj);
			return "success";
		}catch (Exception e) {
			return "failed to update";
		}		}


	//Update book Author List (Admin)
	@PutMapping("/updateBookAuthors")
	public String updateBookAuthors(@RequestBody ArrayList<Map<String, String>> body) {

		for (Map<String, String> map : body) {


			int bookId = Integer.parseInt(map.get("bookId"));
			System.out.println("bookId " + bookId);
			Optional<Book> optional = bookRepository.findById(bookId);
			Book bookObj = optional.get();

			Author authorObj = new Author(map.get("author"));

			Set<Author> authors =bookObj.getAuthors();
			for (Author author : authors) {
				try {	
					author.setName(authorObj.getName());
					authorRepository.save(author);
				}catch (Exception e) {
					return e.toString();
				}	
			}
		}
		return "success";
	}


	// Delete book (admin)
	@DeleteMapping("/deleteBook")
	public boolean deleteBook(@RequestBody Map<String, String> body) {
		int bookId = Integer.parseInt(body.get("bookId"));
		try {
			bookRepository.deleteById(bookId);
			return true;
		}catch (Exception e) {
			return false;
		}

	}

}
