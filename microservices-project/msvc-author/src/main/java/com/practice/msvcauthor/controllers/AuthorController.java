package com.practice.msvcauthor.controllers;

import com.practice.msvcauthor.domain.dto.AuthorDto;
import com.practice.msvcauthor.domain.entities.AuthorEntity;
import com.practice.msvcauthor.http.response.BooksByAuthorResponse;
import com.practice.msvcauthor.mappers.Mapper;
import com.practice.msvcauthor.services.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/author")
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    /**
     * Endpoint used to create an Author in the database
     * @param authorDto Object containing information about the author to be created.
     */
    @PostMapping("/create")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity),
                HttpStatus.CREATED);
    }

    /**
     * Endpoint used to find an Author by its ID, if the Author exists returns the
     * Author and a Http Status 302 found, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     */
    @GetMapping("/search/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor= authorService.findOne(id);

        return foundAuthor.map(existingAuthor->{
            AuthorDto authordto = authorMapper.mapTo(existingAuthor);
            return new ResponseEntity<>(authordto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint that returns all the existing Authors in the database
     */
    @GetMapping("/all")
    public List<AuthorDto> getAllAuthors(){
        return authorService.findAll().stream().map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * Endpoint used to fully update an existing Author found by its ID, if the Author exists returns the
     * updated Author and a Http Status 200 OK, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param authorDto Object containing information about the author to be updated.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<AuthorDto> fullUpdate(
            @PathVariable("id")Long id,
            @RequestBody AuthorDto authorDto){
        authorDto.setId(id);

        if (!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
            AuthorEntity updatedAuthorEntity = authorService.save(authorEntity);
            return new ResponseEntity<>(
                    authorMapper.mapTo(updatedAuthorEntity),
                    HttpStatus.OK);
        }
    }

    /**
     * Endpoint used to partially update an existing Author found by its ID, if the Author exists returns the
     * updated Author and a Http Status 200 OK, if the Author doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param authorDto Object containing information about the author to be updated.
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable("id")Long id,
            @RequestBody AuthorDto authorDto){
        authorDto.setId(id);

        if (!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
            AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);
            return new ResponseEntity<>(
                    authorMapper.mapTo(updatedAuthorEntity),
                    HttpStatus.OK);
        }
    }

    /**
     * Endpoint used to delete an Author by its ID and returns a Http status
     * 204 no content
     * @param id in the endpoint's path
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id")Long id){
        authorService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint used to get all the books that an Author has in the database with the
     * Author's details included, it also returns a Http status 200 Ok
     * @param id in the endpoint's path
     */
    @GetMapping("/books/by/author/{id}")
    public ResponseEntity<BooksByAuthorResponse> getBooksByAuthor(@PathVariable("id")Long id){
        return new ResponseEntity<>(
                authorService.getBookByAuthor(id),
                HttpStatus.OK);
    }



}
