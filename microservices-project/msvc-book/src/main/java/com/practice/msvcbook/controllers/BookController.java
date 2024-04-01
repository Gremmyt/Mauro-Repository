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

    /**
     * Endpoint used to create a Book in the database
     * @param bookDto Object containing information about the Book to be created.
     */
    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto  bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.CREATED);
    }

    /**
     * Endpoint used to find a Book by its ID, if the Book exists returns the
     * Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     */
    @GetMapping("/search/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id")Long id){
        Optional<BookEntity> foundBook = bookService.findOne(id);

        return foundBook.map(existingBook->{
            BookDto bookDto = bookMapper.mapTo(existingBook);
            return new ResponseEntity<>(bookDto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint that returns all the existing Books in the database
     */
    @GetMapping("/all")
    public List<BookDto> findAllBooks(){
        List<BookEntity> bookEntities = bookService.findAll();

        return bookEntities.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Endpoint used to fully update an existing Book found by its ID, if the Book exists returns the
     * updated Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param bookDto Object containing information about the Book to be updated.
     */
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

    /**
     * Endpoint used to partially update an existing Book found by its ID, if the Book exists returns the
     * updated Book and a Http Status 200 OK, if the Book doesn't exist it returns a Http
     * status 404 not found.
     * @param id in the endpoint's path
     * @param bookDto Object containing information about the Book to be updated.
     */
    @PatchMapping("/update/{id}")
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

    /**
     * Endpoint used to delete a Book by its ID, if the Book exists returns a
     * Http Status 204 No Content after it's deleted, if the Book doesn't exist
     * it returns a Http status 404 not found.
     * @param id in the endpoint's path
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id")Long id){
        bookService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint that returns a list of the book an Author has
     * added to the database.
     * @param id int the endpoint's path
     */
    @GetMapping("/all/by/{id}")
    public List<BookDto> getAllByAuthorId(@PathVariable("id")Long id){
        List<BookEntity> allAuthorById = bookService.findAllByAuthorId(id);
        return allAuthorById.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }





}
