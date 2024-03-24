package com.practice.msvcbook.services;

import com.practice.msvcbook.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(BookEntity bookEntity);

    Optional<BookEntity> findOne(Long id);

    List<BookEntity> findAll();

    List<BookEntity> findAllByAuthorId(Long id);

    BookEntity partialUpdate(Long id, BookEntity bookEntity);

    Boolean exist(Long id);

    void delete(Long id);


}
