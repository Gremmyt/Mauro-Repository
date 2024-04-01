package com.practice.msvcbook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.msvcbook.TestDataUtil;
import com.practice.msvcbook.domain.dto.BookDto;
import com.practice.msvcbook.domain.entities.BookEntity;
import com.practice.msvcbook.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private BookService bookService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public BookControllerIntegrationTests(BookService bookService, ObjectMapper objectMapper, MockMvc mockMvc) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    @Test
    public void TestThatCreateReturnHttpStatus200Ok() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA();
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void TestThatCreateReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA();
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Call of Cthulu")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.authorId").value(bookDto.getAuthorId())
        );
    }

    @Test
    public void TestThatGetBookReturnsHttpStatus404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatGetBookReturnsHttpStatus302Found() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isFound()
        );
    }

    @Test
    public void TestThatGetBookReturnsBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Call of Cthulu")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.authorId").value(1L)
        );
    }

    @Test
    public void TestThatFindAllBooksReturnsListOfBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/all")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Call of Cthulu")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].authorId").value(1L)
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus404NotFound() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA();
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createBookDtoA();
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createBookDtoA();
        bookDto.setName("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus404NotFound() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA();
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createBookDtoA();
        bookDto.setName("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createBookDtoA();
        bookDto.setName("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        );
    }

    @Test
    public void TestThatDeleteByIdReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/book/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }



}



































