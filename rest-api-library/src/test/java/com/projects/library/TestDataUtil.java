package com.projects.library;

import com.projects.library.domain.dto.AuthorDto;
import com.projects.library.domain.entities.AuthorEntity;

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


}
