package com.backend.LibraryManagementSystem.DTO;

import com.backend.LibraryManagementSystem.Enum.Departmant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentRequestDto {
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Departmant departmant;

    private String email;
}
