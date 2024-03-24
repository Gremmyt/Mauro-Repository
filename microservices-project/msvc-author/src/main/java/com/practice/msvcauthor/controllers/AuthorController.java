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

    @PostMapping("/create")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity),
                HttpStatus.CREATED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor= authorService.findOne(id);

        return foundAuthor.map(existingAuthor->{
            AuthorDto authordto = authorMapper.mapTo(existingAuthor);
            return new ResponseEntity<>(authordto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public List<AuthorDto> getAllAuthors(){
        return authorService.findAll().stream().map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id")Long id){
        authorService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/books/by/author/{id}")
    public ResponseEntity<BooksByAuthorResponse> getBooksByAuthor(@PathVariable("id")Long id){
        return new ResponseEntity<>(
                authorService.getBookByAuthor(id),
                HttpStatus.OK);
    }



}
