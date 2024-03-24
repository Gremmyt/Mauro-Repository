package com.practice.msvcauthor.client;

import com.practice.msvcauthor.domain.dto.BookDto;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-book", url = "localhost:9090/api/book")
public interface BookClient {

    @GetMapping("/all/by/{id}")
    List<BookDto> findAllBooksByAuthor(@PathVariable("id")Long id);
}
