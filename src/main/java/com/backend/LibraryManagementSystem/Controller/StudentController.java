package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.StudentRequestDto;
import com.backend.LibraryManagementSystem.DTO.StudentResponseDto;
import com.backend.LibraryManagementSystem.DTO.StudentUpdateRequestDto;
import com.backend.LibraryManagementSystem.Entity.Student;
import com.backend.LibraryManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public StudentResponseDto addStudent(@RequestBody StudentRequestDto studentRequestDto){
        return studentService.addStudent(studentRequestDto);
    }

    @GetMapping("/find_by_email")
    public String findStudentByEmail(@RequestParam("email") String email){
        return studentService.findStudentByEmail(email);
    }

    @GetMapping("/get_by_same_age")
    public List<String> getBySameAge(@RequestParam("age") int age){
        return studentService.getBySameAge(age);
    }

    @PutMapping("update_email")
    public StudentResponseDto updateStudentEmail(@RequestBody StudentUpdateRequestDto studentUpdateRequestDto){
        return studentService.updateStudentEmail(studentUpdateRequestDto);
    }
}
