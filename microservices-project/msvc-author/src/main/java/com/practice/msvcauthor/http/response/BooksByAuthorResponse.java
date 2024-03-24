package com.practice.msvcauthor.http.response;

import com.practice.msvcauthor.domain.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BooksByAuthorResponse {

    private String authorName;
    private Integer age;
    private List<BookDto> bookList;

}
