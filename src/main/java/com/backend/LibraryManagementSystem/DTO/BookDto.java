package com.backend.LibraryManagementSystem.DTO;

import com.backend.LibraryManagementSystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String tittle;

    private int price;

    private Genre genre;

    private boolean isIssued;
}
