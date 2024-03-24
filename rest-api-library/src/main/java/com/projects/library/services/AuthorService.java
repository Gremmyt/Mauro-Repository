package com.projects.library.services;

import com.projects.library.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    Optional<AuthorEntity> findById(Long id);

    List<AuthorEntity> findAll();

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    boolean existsById(Long id);

    void deleteById(Long id);

}
