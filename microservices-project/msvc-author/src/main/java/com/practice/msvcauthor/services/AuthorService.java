package com.practice.msvcauthor.services;

import com.practice.msvcauthor.domain.entities.AuthorEntity;
import com.practice.msvcauthor.http.response.BooksByAuthorResponse;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    Optional<AuthorEntity> findOne(Long id);

    List<AuthorEntity> findAll();

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    Boolean isExist(Long id);

    void deleteById(Long id);


    BooksByAuthorResponse getBookByAuthor(Long id);
}
