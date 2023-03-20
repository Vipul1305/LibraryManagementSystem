package com.backend.LibraryManagementSystem.Entity;

import com.backend.LibraryManagementSystem.Enum.Departmant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Departmant departmant;

    private String email;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    LibraryCard card;

}
