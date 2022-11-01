package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) throws Exception {
        //check if email is taken
        Boolean existEmail = studentRepository.selectExistsEmail(student.getEmail());
        if (existEmail)
            throw new BadRequestException("the email address: " + student.getEmail() + " already exist");

//        if(studentRepository.existsByEmail(student.getEmail()))
//           throw new BadRequestException("the email address: " + student.getEmail() + " already exist");
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        //check if student exists
        if (!studentRepository.existsById(id)){
            throw new StudentNotFoundException("student with studentId: " + id + " does not exist");
        }
        studentRepository.deleteById(id);

    }
}
