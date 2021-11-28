package com.example.vismabooklibrary.dto.response;

import com.example.vismabooklibrary.entities.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateBookResponse {

    private String name;

    private String category;

    private String language;

    private String author;

    private LocalDate publicationDate;

    private String isbn;

    private UUID guid;

    public CreateBookResponse(Book book) {
        this.name = book.getName();
        this.category = book.getCategory();
        this.language = book.getLanguage();
        this.author = book.getAuthor();
        this.publicationDate = book.getPublicationDate();
        this.isbn = book.getIsbn();
        this.guid = book.getGuid();
    }
}
