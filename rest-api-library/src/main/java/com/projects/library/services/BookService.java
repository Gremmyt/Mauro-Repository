package com.projects.library.services;

import com.projects.library.domain.entities.BookEntity;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findById(Long id);

    BookEntity partialUpdate(Long id, BookEntity bookEntity);

    boolean existsById(Long id);

    void deleteById(Long id);


}
