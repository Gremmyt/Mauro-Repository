package com.projects.library.controllers;


import com.projects.library.domain.entities.BookEntity;
import com.projects.library.domain.dto.BookDto;
import com.projects.library.mappers.Mapper;
import com.projects.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private Mapper<BookEntity, BookDto> bookMapper;

    /**
     * Endpoint used to create a Book in the database
     * @param bookDto Object containing information about the Book to be created.
     */
    @PostMapping
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.CREATED);
    }

    /**
     * Endpoint that returns all the existing Books in the database
     */
    @GetMapping
    public List<BookDto> findAll(){
        return bookService.findAll().stream()
                .map(bookMapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Endpoint used to find a Book by its ID, if the Book exists returns the
     * Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id){
        if (!bookService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookService.findById(id).orElseThrow();
        return new ResponseEntity<>(
                bookMapper.mapTo(bookEntity),
                HttpStatus.OK);
    }

    /**
     * Endpoint used to fully update an existing Book found by its ID, if the Book exists returns the
     * updated Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param bookDto Object containing information about the Book to be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> fullUpdate(
            @RequestBody BookDto bookDto,
            @PathVariable Long id){
        if (!bookService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setId(id);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBook = bookService.save(bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(updatedBook),
                HttpStatus.OK);
    }

    /**
     * Endpoint used to partially update an existing Book found by its ID, if the Book exists returns the
     * updated Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param bookDto Object containing information about the Book to be updated.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> partialUpdate(
            @RequestBody BookDto bookDto,
            @PathVariable Long id){
        if (!bookService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setId(id);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBook = bookService.partialUpdate(id, bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(updatedBook),
                HttpStatus.OK);
    }

    /**
     * Endpoint used to delete a Book by its ID, if the Book exists returns a
     * Http Status 204 No Content after it's deleted, if the Book doesn't exist
     * it returns a Http status 404 not found.
     * @param id in the endpoint's path
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (!bookService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }











}
