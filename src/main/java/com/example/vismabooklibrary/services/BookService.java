package com.example.vismabooklibrary.services;

import com.example.vismabooklibrary.entities.Book;
import com.example.vismabooklibrary.exceptions.ResourceNotFoundException;
import com.example.vismabooklibrary.util.BookSort;
import com.example.vismabooklibrary.util.JsonParse;
import com.example.vismabooklibrary.validations.BookValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class BookService {

    public List<Book> getBooks() throws IOException {
        return JsonParse.ParsJsonToObjectGetAvailableBooks();
    }

    public Book createBook(Book book) {
        return JsonParse.AddObjectToJsonSaveNewBook(book);
    }

    public void deleteBookByIsbn(String isbn) throws IOException {
        List<Book> listOfBooks = JsonParse.ParsJsonToObjectGetAvailableBooks();
        if (!listOfBooks.removeIf(book -> book.getIsbn().equals(isbn))) {
            throw new ResourceNotFoundException(isbn);
        }
        JsonParse.DeleteJsonFile();
        for (Book b : listOfBooks) {
            JsonParse.AddObjectToJsonSaveNewBook(b);
        }
    }

    public Book getBookByGuid(UUID guid) throws IOException {
        List<Book> bookByGuid = JsonParse.ParsJsonToObjectGetAvailableBooks();
        return bookByGuid.stream()
                .filter(book -> book.getGuid().equals(guid))
                .findFirst()
                .orElse(null);
    }

    public Stream<Book> sortedBy(int sortedBy) throws IOException {
        List<Book> listOfBooks = JsonParse.ParsJsonToObjectGetAvailableBooks();

        switch (sortedBy) {
            case 1:
                return listOfBooks.stream().sorted(Comparator.comparing(Book::getAuthor));
            case 2:
                return listOfBooks.stream().sorted(Comparator.comparing(Book::getName));
            case 3:
                return listOfBooks.stream().sorted(Comparator.comparing(Book::getIsbn));
            case 4:
                return listOfBooks.stream().sorted(Comparator.comparing(Book::getCategory));
            case 5:
                return listOfBooks.stream().sorted(Comparator.comparing(Book::getLanguage));
            case 6:
                return sortBooksByAvailableAndTaken(listOfBooks).stream();
            default:
                throw new IllegalArgumentException
                        ("Invalid sort type: " + sortedBy);
        }
    }

    public Book rentBook(String isbn, String name, LocalDate date) throws IOException {

        List<Book> currentBookList = JsonParse.ParsJsonToObjectGetAvailableBooks();

        Book wantedBook = currentBookList.stream()
                .filter(books -> books.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() ->new ResourceNotFoundException(isbn));

        if (BookValidator.checkDays(date)) {
            if (BookValidator.checkBookAmount(currentBookList, name)) {
                Objects.requireNonNull(wantedBook).setTimeOfRent(date);
                wantedBook.setNameOfBookHolder(name);

                deleteBookByIsbn(wantedBook.getIsbn());
                JsonParse.AddObjectToJsonSaveNewBook(wantedBook);
            }
        }
        return wantedBook;
    }

    public List<Book> sortBooksByAvailableAndTaken(List<Book> listOfBooks) {

        listOfBooks.sort(new BookSort());

        return listOfBooks;
    }
}
