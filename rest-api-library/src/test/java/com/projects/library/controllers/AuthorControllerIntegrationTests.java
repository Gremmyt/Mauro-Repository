package com.projects.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.library.TestDataUtil;
import com.projects.library.domain.dto.AuthorDto;
import com.projects.library.domain.entities.AuthorEntity;
import com.projects.library.services.AuthorService;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThatCreateAuthorReturnsHttpStatus201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorEntity.setId(null);
        String jsonAuthorEntity = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorEntity)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorReturnsCreatedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setId(null);
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("H.P. Lovecraft")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(46)
        );
    }

    @Test
    public void testThatFindAuthorByIdReturnsHttpStatus404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFindAuthorByIdReturnsHttpStatus4200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors/" + authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorByIdReturnsAuthorWhenExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors/" + authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("H.P. Lovecraft")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(46)
        );
    }

    @Test
    public void testThatGetAllAuthorsReturnsAllAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("H.P. Lovecraft")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(46)
        );
    }

    @Test
    public void testThatFullUpdateReturnsHttpStatus404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+ authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus404NotFound() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");
        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus404NotFoundWhenAuthorDoesNotExist() throws Exception {
        mockMvc.perform(
              MockMvcRequestBuilders.delete("/api/authors/1")
                      .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204NoContentForExistingAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }








}
