package com.example.vismabooklibrary.entities;

import com.example.vismabooklibrary.dto.BookDTO;
import com.example.vismabooklibrary.dto.request.CreateBookRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate publicationDate;

    private String name;

    private String category;

    private String language;

    private String author;

    private String isbn;

    private UUID guid;

    private String nameOfBookHolder = "default value";

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate timeOfRent;

    public Book(LocalDate publicationDate, String name, String category,
                String language, String author, String isbn) {
        this.name = name;
        this.category = category;
        this.language = language;
        this.author = author;
        this.publicationDate = publicationDate;
        this.isbn = isbn;

    }

    public Book(CreateBookRequest createBookRequest) {
        this.guid = UUID.randomUUID();
        this.name = createBookRequest.getName();
        this.category = createBookRequest.getCategory();
        this.language = createBookRequest.getLanguage();
        this.author = createBookRequest.getAuthor();
        this.publicationDate = createBookRequest.getPublicationDate();
        this.isbn = createBookRequest.getIsbn();
    }

    public Book(BookDTO bookDTO) {
        this.guid = bookDTO.getGuid();
        this.name = bookDTO.getName();
        this.category = bookDTO.getCategory();
        this.language = bookDTO.getLanguage();
        this.author = bookDTO.getAuthor();
        this.publicationDate = bookDTO.getPublicationDate();
        this.isbn = bookDTO.getIsbn();
    }

}
