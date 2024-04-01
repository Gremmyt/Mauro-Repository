package com.practice.msvcauthor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.msvcauthor.TestDataUtil;
import com.practice.msvcauthor.domain.dto.AuthorDto;
import com.practice.msvcauthor.domain.entities.AuthorEntity;
import com.practice.msvcauthor.services.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(AuthorService authorService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.authorService = authorService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void TestThatCreateAuthorReturnsHttpStatus201Created() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void TestThatCreateAuthorReturnsCreatedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void TestThatGetAuthorReturnsHttpStatus404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/author/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatGetAuthorReturnsHttpStatus302Found() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/author/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isFound()
        );
    }

    @Test
    public void TestThatGetAuthorReturnsFoundAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/author/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void TestThatGetAllAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/author/all")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsHttpStatus200Ok() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatFullUpdateReturnsUpdatedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsHttpStatus200Ok() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatPartialUpdateReturnsUpdatedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/author/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void TestThatDeleteAuthorReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/author/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }










}
