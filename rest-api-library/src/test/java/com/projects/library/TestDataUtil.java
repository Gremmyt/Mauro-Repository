package com.projects.library;

import com.projects.library.domain.dto.AuthorDto;
import com.projects.library.domain.dto.BookDto;
import com.projects.library.domain.entities.AuthorEntity;
import com.projects.library.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static AuthorEntity createTestAuthorEntityA(){
        return AuthorEntity.builder()
                .id(1L)
                .name("H.P. Lovecraft")
                .age(46)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA(){
        return AuthorDto.builder()
                .id(1L)
                .name("H.P. Lovecraft")
                .age(46)
                .build();
    }

    public static BookEntity createTestBookEntityA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .id(1L)
                .title("Some Book")
                .isbn("rafe-eaf-faf")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .id(1L)
                .title("Some Book")
                .isbn("rafe-eaf-faf")
                .author(authorDto)
                .build();
    }

    public static AuthorEntity createTestAuthorB(){
        return AuthorEntity.builder()
                .id(2L)
                .name("Coelho")
                .age(67)
                .build();
    }

    public static AuthorEntity createTestAuthorC(){
        return AuthorEntity.builder()
                .id(3L)
                .name("Tolkien")
                .age(80)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .id(2L)
                .title("Second Book")
                .isbn("btyru-segr-rt")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .id(3L)
                .title("Third Book")
                .isbn("ade-teey-w")
                .authorEntity(authorEntity)
                .build();
    }



}
