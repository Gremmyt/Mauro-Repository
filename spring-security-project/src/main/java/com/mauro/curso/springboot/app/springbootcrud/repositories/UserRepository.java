package com.mauro.curso.springboot.app.springbootcrud.repositories;

import com.mauro.curso.springboot.app.springbootcrud.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}

