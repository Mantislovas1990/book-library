package com.example.vismabooklibrary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    private String name;

    private String category;

    private String language;

    private String author;

    private LocalDate publicationDate;

    private String isbn;

}
