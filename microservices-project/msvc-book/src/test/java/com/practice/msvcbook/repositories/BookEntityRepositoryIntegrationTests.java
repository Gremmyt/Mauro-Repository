package com.practice.msvcbook.repositories;

import com.practice.msvcbook.TestDataUtil;
import com.practice.msvcbook.domain.entities.BookEntity;
import com.practice.msvcbook.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void TestThatBookCanBeCreatedAndRecalled(){
        BookEntity bookEntity = TestDataUtil.createBookEntityA();
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void TestThatMultipleBooksCanBeCreatedAndRecalled(){
        BookEntity bookEntityA = TestDataUtil.createBookEntityA();
        underTest.save(bookEntityA);

        BookEntity bookEntityB = TestDataUtil.createBookEntityB();
        underTest.save(bookEntityB);

        BookEntity bookEntityC = TestDataUtil.createBookEntityC();
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void TestThatBookCanBeUpdated() {
        BookEntity bookEntityA = TestDataUtil.createBookEntityA();
        underTest.save(bookEntityA);

        bookEntityA.setTitle("UPDATED");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getId());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        BookEntity bookEntityA = TestDataUtil.createBookEntityA();
        underTest.save(bookEntityA);

        underTest.deleteById(bookEntityA.getId());

        Optional<BookEntity> result = underTest.findById(bookEntityA.getId());
        Assertions.assertThat(result).isEmpty();
    }



}
