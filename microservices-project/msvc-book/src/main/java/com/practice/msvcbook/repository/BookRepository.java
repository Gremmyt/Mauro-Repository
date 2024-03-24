package com.practice.msvcbook.repository;

import com.practice.msvcbook.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {
    Iterable<BookEntity> findAllByAuthorId(Long id);
}
