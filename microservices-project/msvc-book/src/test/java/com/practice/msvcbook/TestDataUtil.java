package com.practice.msvcbook;

import com.practice.msvcbook.domain.dto.BookDto;
import com.practice.msvcbook.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static BookEntity createBookEntityA(){
        return BookEntity.builder()
                .id(1L)
                .title("Call of Cthulu")
                .authorId(1L)
                .build();
    }

    public static BookDto createBookDtoA(){
        return BookDto.builder()
                .id(1L)
                .name("Call of Cthulu")
                .authorId(1L)
                .build();
    }

    public static BookEntity createBookEntityB(){
        return BookEntity.builder()
                .id(2L)
                .title("Second Book")
                .authorId(2L)
                .build();
    }

    public static BookEntity createBookEntityC(){
        return BookEntity.builder()
                .id(3L)
                .title("Third Book")
                .authorId(3L)
                .build();
    }


}
