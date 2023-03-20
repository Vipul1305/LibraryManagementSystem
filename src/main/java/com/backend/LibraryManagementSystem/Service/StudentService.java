package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.StudentRequestDto;
import com.backend.LibraryManagementSystem.DTO.StudentResponseDto;
import com.backend.LibraryManagementSystem.DTO.StudentUpdateRequestDto;
import com.backend.LibraryManagementSystem.Entity.LibraryCard;
import com.backend.LibraryManagementSystem.Entity.Student;
import com.backend.LibraryManagementSystem.Enum.CardStatus;
import com.backend.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto){

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setDepartmant(studentRequestDto.getDepartmant());
        student.setEmail(studentRequestDto.getEmail());

        // set the value of card
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);

        // set the card attribute in student
        student.setCard(card);

        studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setEmail(student.getEmail());

        return studentResponseDto;
    }
    public String findStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email);
        return student.getName();
    }
    public List<String> getBySameAge(int age){
        List<Student> studentList = studentRepository.findByAge(age);
        List<String> list = new ArrayList<>();
        for(Student student: studentList){
            list.add(student.getName());
        }
        return list;
    }
    public StudentResponseDto updateStudentEmail(StudentUpdateRequestDto studentUpdateRequestDto){
        Student student = studentRepository.findById(studentUpdateRequestDto.getId()).get();
        student.setEmail(studentUpdateRequestDto.getEmail());

        Student updatedStudent = studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }
}
