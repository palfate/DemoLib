package com.demo.library.application.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Books")
public class Book{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
	    private int id;
		
		@Column(name = "Title")
	    private String title;
		
		@ManyToMany()
	    @JoinTable(name = "BookAuthor", joinColumns = @JoinColumn(name = "bookid"), inverseJoinColumns = @JoinColumn(name = "authorid"))
	    private Set<Author> authors;
   
		public Book() {}
	    
		public Book(String title) {
			super();
			this.title = title;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		

		public Set<Author> getAuthors() {
			return authors;
		}
		 public void setAuthors(Set<Author> authors) {
			this.authors = authors;
		}


		@Override
		public String toString() {
			return "Book [id=" + id + ", title=" + title + "]";
		}
}
