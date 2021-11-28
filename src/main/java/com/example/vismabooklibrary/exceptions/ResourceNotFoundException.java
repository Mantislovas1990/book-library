package com.example.vismabooklibrary.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String isbn;

    public ResourceNotFoundException(String isbn) {
        this.isbn = isbn;
    }
}
