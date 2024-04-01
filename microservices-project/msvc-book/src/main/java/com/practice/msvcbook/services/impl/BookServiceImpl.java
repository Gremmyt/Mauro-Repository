package com.practice.msvcbook.services.impl;

import com.practice.msvcbook.domain.entities.BookEntity;
import com.practice.msvcbook.repository.BookRepository;
import com.practice.msvcbook.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public BookEntity save(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public Optional<BookEntity> findOne(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll()
                .spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<BookEntity> findAllByAuthorId(Long id) {
        return StreamSupport.stream(bookRepository.findAllByAuthorId(id)
                .spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public BookEntity partialUpdate(Long id, BookEntity bookEntity) {
        bookEntity.setId(id);

        return bookRepository.findById(id).map(existBook->{
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existBook::setTitle);
            Optional.ofNullable(bookEntity.getAuthorId()).ifPresent(existBook::setAuthorId);
            BookEntity updatedBookEntity = bookRepository.save(existBook);
            return updatedBookEntity;
        }).orElseThrow(()->new RuntimeException("Book does not exist"));
    }

    @Override
    public Boolean exist(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
