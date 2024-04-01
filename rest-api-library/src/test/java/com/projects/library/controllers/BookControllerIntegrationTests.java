package com.projects.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.library.TestDataUtil;
import com.projects.library.domain.dto.AuthorDto;
import com.projects.library.domain.dto.BookDto;
import com.projects.library.domain.entities.AuthorEntity;
import com.projects.library.domain.entities.BookEntity;
import com.projects.library.services.AuthorService;
import com.projects.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.print.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void TestThatSaveBookReturnsHttpStatusCreated() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void TestThatSaveBookReturnsSavedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Some Book")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("rafe-eaf-faf")
        );
    }

    @Test
    public void TestThatFindAllReturnsBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("Some Book")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("rafe-eaf-faf")
        );
    }

    @Test
    public void TestThatFindByIdReturnsHttpStatus404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatFindByIdReturnsBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Some Book")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("rafe-eaf-faf")
        );
    }

    @Test
    public void TestThatFindByIdReturns200StatusOK() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus404NotFound() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus200OK() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsUpdatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Some Book")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("rafe-eaf-faf")
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus404NotFound() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("UPDATED");
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("rafe-eaf-faf")
        );
    }

    @Test
    public void TestThatDeleteBookReturnsHttpStatus404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatDeleteBookReturnsHttpStatus204NoContent() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }



}
