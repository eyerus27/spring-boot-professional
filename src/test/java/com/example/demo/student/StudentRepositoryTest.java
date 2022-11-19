package com.example.demo.student;

import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;



    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldTestIfStudentExistsByEmail() {
        //given
        Student student = new Student(
                "Helen", "helen@gmail.com", Gender.FEMALE

        );
        underTest.save(student);

        //when
        boolean exists = underTest.existsByEmail(student.getEmail());

        //then
        assertThat(exists).isTrue();

    }

    @Test
    void itShouldSelectIfEmailExists() {

        //given
        Student student = new Student(
                "Helen", "helen@gmail.com", Gender.FEMALE

        );
        underTest.save(student);

        //when
        boolean exists = underTest.selectExistsEmail(student.getEmail());

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckIfEmailDoesNotExists() {

        //given
        String email = "helen@gmail.com";

        //when
        boolean exists = underTest.selectExistsEmail(email);

        //then
        assertThat(exists).isFalse();
    }
}