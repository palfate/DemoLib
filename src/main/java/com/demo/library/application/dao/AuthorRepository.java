package com.demo.library.application.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.library.application.domain.Author;
import com.demo.library.application.domain.Book;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
