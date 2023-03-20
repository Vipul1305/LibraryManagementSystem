package com.backend.LibraryManagementSystem.Repository;

import com.backend.LibraryManagementSystem.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    //custom method
    Student findByEmail(String email);
    List<Student> findByAge(int age);
}
