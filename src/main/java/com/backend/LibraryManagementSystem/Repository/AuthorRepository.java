package com.backend.LibraryManagementSystem.Repository;

import com.backend.LibraryManagementSystem.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
