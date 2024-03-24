package com.practice.msvcauthor.services.impl;

import com.practice.msvcauthor.client.BookClient;
import com.practice.msvcauthor.domain.dto.BookDto;
import com.practice.msvcauthor.domain.entities.AuthorEntity;
import com.practice.msvcauthor.http.response.BooksByAuthorResponse;
import com.practice.msvcauthor.repositories.AuthorRepository;
import com.practice.msvcauthor.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    private BookClient bookClient;

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository.findAll()
                .spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRepository.findById(id).map(existAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existAuthor::setAge);
            return authorRepository.save(existAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public Boolean isExist(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public BooksByAuthorResponse getBookByAuthor(Long id) {

        //Consulting author
        AuthorEntity authorEntity = authorRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Author does not exist"));

        //Obtaining books
        List<BookDto> bookDtoList = bookClient.findAllBooksByAuthor(id);

        return BooksByAuthorResponse.builder()
                .authorName(authorEntity.getName())
                .age(authorEntity.getAge())
                .bookList(bookDtoList)
                .build();
    }
}
