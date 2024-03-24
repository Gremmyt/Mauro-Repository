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

    @PostMapping
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.CREATED);
    }

    @GetMapping
    public List<BookDto> findAll(){
        return bookService.findAll().stream()
                .map(bookMapper::mapTo).collect(Collectors.toList());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (!bookService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }











}
