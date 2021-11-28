package com.example.vismabooklibrary.controllers;

import com.example.vismabooklibrary.dto.BookDTO;
import com.example.vismabooklibrary.dto.request.CreateBookRequest;
import com.example.vismabooklibrary.dto.response.CreateBookResponse;
import com.example.vismabooklibrary.entities.Book;
import com.example.vismabooklibrary.services.BookService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() throws IOException {
        return bookService.getBooks().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{guid}")
    public BookDTO getOneBookByGuid(@RequestBody @PathVariable("guid") UUID guid) throws IOException {
        return new BookDTO(bookService.getBookByGuid(guid));
    }

    @PostMapping(path = "/create")
    public CreateBookResponse createBook(
            @RequestBody CreateBookRequest createBookRequest) {
        return new CreateBookResponse(bookService.createBook(new Book(createBookRequest)));
    }

    @GetMapping("/sort/{sort}")
    public List<BookDTO> getSortedBookListBy(@PathVariable("sort") int sorting) throws IOException {
        return bookService.sortedBy(sorting).map(BookDTO::new).collect(Collectors.toList());
    }

    @PostMapping(path = "/rent/{isbn}")
    public Book rentBookFromLibrary(
            @PathVariable("isbn") String isbn,
            @RequestParam("name") String name,
            @RequestParam("timeOfRent")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate timeOfRenting) throws IOException {

        return bookService.rentBook(isbn, name, timeOfRenting);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBookByIsbn(@PathVariable("isbn") String isbn) throws IOException {
        bookService.deleteBookByIsbn(isbn);
    }
}
