package com.projects.library.controllers;

import com.projects.library.domain.dto.AuthorDto;
import com.projects.library.domain.entities.AuthorEntity;
import com.projects.library.mappers.Mapper;
import com.projects.library.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    @Autowired
    private AuthorService authorService;

    /**
     * Endpoint used to create an Author in the database
     * @param authorDto Object containing information about the author to be created.
     */
    @PostMapping
    public ResponseEntity<AuthorDto> save(@RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity),
                HttpStatus.CREATED);
    }

    /**
     * Endpoint used to find an Author by its ID, if the Author exists returns the
     * Author and a Http Status 200 OK, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> findById(@PathVariable Long id){
        if (!authorService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorService.findById(id).orElseThrow();
        return new ResponseEntity<>(
                authorMapper.mapTo(authorEntity),
                HttpStatus.OK);
    }

    /**
     * Endpoint that returns all the existing Authors in the database
     */
    @GetMapping
    public List<AuthorDto> findAll(){
        return authorService.findAll()
                .stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Endpoint used to fully update an existing Author found by its ID, if the Author exists returns the
     * updated Author and a Http Status 200 OK, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param authorDto Object containing information about the author to be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> fullUpdate(
            @RequestBody AuthorDto authorDto,
            @PathVariable Long id){
        if (!authorService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(updatedAuthorEntity),
                HttpStatus.OK);
    }

    /**
     * Endpoint used to partially update an existing Author found by its ID, if the Author exists returns the
     * updated Author and a Http Status 200 OK, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param authorDto Object containing information about the author to be updated.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @RequestBody AuthorDto authorDto,
            @PathVariable Long id){
        authorDto.setId(id);
        if (!authorService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(updatedAuthorEntity),
                HttpStatus.OK);
    }

    /**
     * Endpoint used to delete an Author by its ID, if the Author exists returns a
     * Http Status 204 No Content after it's deleted, if the Author doesn't exist
     * it returns a Http status 404 not found.
     * @param id in the endpoint's path
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (!authorService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
