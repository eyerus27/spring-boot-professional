package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
//initialize the mock
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {

        underTest = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void CanAddStudent() throws Exception {
        //given
        Student student = new Student(
                "Helen", "helen@gmail.com", Gender.FEMALE

        );
        //when
        underTest.addStudent(student);

        //then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());
        Student capturedStudent = argumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void willThrowExceptionWhenEmailIsTaken() throws Exception {
        //given
        Student student = new Student(
                "Helen", "helen@gmail.com", Gender.FEMALE

        );
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //when then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("the email address: " + student.getEmail() + " already exist");
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() throws Exception {
        //given
        long id =1001;
        given(studentRepository.existsById(id)).willReturn(true);
        //when
        underTest.deleteStudent(id);
        // Then
        verify(studentRepository).deleteById(id);

    }
    @Test
    void willThrowExceptionIfIdDoesntExist() throws Exception {
        long id =1001;
      assertThatThrownBy(() ->underTest.deleteStudent(id))
              .isInstanceOf(StudentNotFoundException.class)
              .hasMessageContaining("student with studentId: " + id + " does not exist");
      verify(studentRepository, never()).deleteById(id);

    }
}