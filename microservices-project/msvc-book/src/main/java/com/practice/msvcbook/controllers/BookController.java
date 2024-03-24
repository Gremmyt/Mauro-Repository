package com.practice.msvcbook.controllers;

import com.practice.msvcbook.domain.dto.BookDto;
import com.practice.msvcbook.domain.entities.BookEntity;
import com.practice.msvcbook.mappers.Mapper;
import com.practice.msvcbook.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto  bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.CREATED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id")Long id){
        Optional<BookEntity> foundBook = bookService.findOne(id);

        return foundBook.map(existingBook->{
            BookDto bookDto = bookMapper.mapTo(existingBook);
            return new ResponseEntity<>(bookDto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public List<BookDto> findAllBooks(){
        List<BookEntity> bookEntities = bookService.findAll();

        return bookEntities.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDto> fullUpdate(
            @PathVariable("id")Long id,
            @RequestBody BookDto bookDto){
        bookDto.setId(id);

        if (!bookService.exist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity updatedBookEntity = bookService.save(bookEntity);
            return new ResponseEntity<>(
                    bookMapper.mapTo(updatedBookEntity),
                    HttpStatus.OK);
        }

    }

    @PatchMapping("/update{id}")
    public ResponseEntity<BookDto> partialUpdate(
            @PathVariable("id")Long id,
            @RequestBody BookDto bookDto){
        bookDto.setId(id);

        if (!bookService.exist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity partialUpdateBookEntity = bookService.partialUpdate(id, bookEntity);
            return new ResponseEntity<>(
                    bookMapper.mapTo(partialUpdateBookEntity),
                    HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id")Long id){
        bookService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/by/{id}")
    public List<BookDto> getAllByAuthorId(@PathVariable("id")Long id){
        List<BookEntity> allAuthorById = bookService.findAllByAuthorId(id);
        return allAuthorById.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }





}
