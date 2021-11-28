package com.example.vismabooklibrary.bookControllerIntegrationTests;

import com.example.vismabooklibrary.dto.request.CreateBookRequest;
import com.example.vismabooklibrary.dto.response.CreateBookResponse;
import com.example.vismabooklibrary.entities.Book;
import com.example.vismabooklibrary.services.BookService;
import com.example.vismabooklibrary.util.BookSort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetAllBook() throws Exception {

        when(bookService.getBooks()).thenReturn(listOfBooks());

        MvcResult mvcResult = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> listOfBooks = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        int sizeOfList = listOfBooks.size();

        assertEquals(3, sizeOfList);
    }

    @Test
    void ShouldGetOneBookByGuid() throws Exception {

        Book book = new Book(LocalDate.now(), "Name", "Category", "Language", "Author", "1");

        book.setGuid(UUID.randomUUID());

        when(bookService.getBookByGuid(book.getGuid())).thenReturn(book);

        MvcResult mvcResult = mockMvc.perform(get("/books/" + book.getGuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(book)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Book bookByGuid = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);

        UUID booksUUID = book.getGuid();

        assertEquals(bookByGuid.getGuid(), booksUUID);
    }

    @Test
    void shouldCreateNewBook() throws Exception {

        Book book = new Book(LocalDate.now(), "Name", "Category", "Language", "Author", "1");
        CreateBookRequest createBookRequest = new CreateBookRequest(
                "Name", "Category", "Language", "Author", LocalDate.now(), "1");

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        MvcResult mvcResult = mockMvc.perform(post("/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(createBookRequest)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        CreateBookResponse createBookResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CreateBookResponse.class);

        assertEquals(createBookResponse.getName(), createBookRequest.getName());
    }

    @Test
    void shouldSortBooksByAuthor() throws Exception {

        when(bookService.sortedBy(1)).thenReturn(listOfBooks().stream().sorted(Comparator.comparing(Book::getAuthor)));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByAuthor = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String authorOfTheBookAtIndex0 = sortedListOfBooksByAuthor.get(0).getAuthor();

        assertEquals("Annto", authorOfTheBookAtIndex0);
    }

    @Test
    void shouldSortBooksByCategory() throws Exception {

        when(bookService.sortedBy(4)).thenReturn(listOfBooks().stream().sorted(Comparator.comparing(Book::getCategory)));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByCategory = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String categoryOfTheBookAtIndex0 = sortedListOfBooksByCategory.get(0).getCategory();

        assertEquals("Adventure", categoryOfTheBookAtIndex0);
    }

    @Test
    void shouldSortBooksByLanguage() throws Exception {

        when(bookService.sortedBy(5)).thenReturn(listOfBooks().stream().sorted(Comparator.comparing(Book::getLanguage)));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByLanguage = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String languageOfTheBookAtIndex0 = sortedListOfBooksByLanguage.get(0).getLanguage();
        String languageOfTheBookAtIndex1 = sortedListOfBooksByLanguage.get(1).getLanguage();
        String languageOfTheBookAtIndex2 = sortedListOfBooksByLanguage.get(2).getLanguage();

        assertEquals("English", languageOfTheBookAtIndex0);
        assertEquals("Lithuanian", languageOfTheBookAtIndex1);
        assertEquals("Russian", languageOfTheBookAtIndex2);
    }

    @Test
    void shouldSortBooksByIsbn() throws Exception {

        when(bookService.sortedBy(3)).thenReturn(listOfBooks().stream().sorted(Comparator.comparing(Book::getIsbn)));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByIsbn = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String isbnOfTheBookAtIndex0 = sortedListOfBooksByIsbn.get(0).getIsbn();
        String isbnOfTheBookAtIndex1 = sortedListOfBooksByIsbn.get(1).getIsbn();
        String isbnOfTheBookAtIndex2 = sortedListOfBooksByIsbn.get(2).getIsbn();

        assertEquals("1", isbnOfTheBookAtIndex0);
        assertEquals("2", isbnOfTheBookAtIndex1);
        assertEquals("3", isbnOfTheBookAtIndex2);
    }

    @Test
    void shouldSortBooksByBookName() throws Exception {

        when(bookService.sortedBy(2)).thenReturn(listOfBooks().stream().sorted(Comparator.comparing(Book::getName)));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooks())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByName = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String nameOfTheBookAtIndex0 = sortedListOfBooksByName.get(0).getName();
        String nameOfTheBookAtIndex1 = sortedListOfBooksByName.get(1).getName();
        String nameOfTheBookAtIndex2 = sortedListOfBooksByName.get(2).getName();

        assertEquals("FirstBook", nameOfTheBookAtIndex0);
        assertEquals("SecondBook", nameOfTheBookAtIndex1);
        assertEquals("ThirdBook", nameOfTheBookAtIndex2);
    }

    @Test
    void shouldSortBooksByAvailableAndTaken() throws Exception {

        List<Book> listOfBooksWithTakenBookAdded = new ArrayList<>();

        Book book1 = new Book(LocalDate.now(), "SecondBook", "Comedy", "Russian", "Woody", "2");
        Book book2 = new Book(LocalDate.now(), "FirstBook", "Horror", "English", "Bob", "1");
        Book book3 = new Book(LocalDate.now(), "ThirdBook", "Adventure", "Lithuanian", "Annto", "3");
        Book bookWithHolderNameAndTimeOfRent = new Book(LocalDate.now(), "Holders Book", "Comedy", "Russian", "Woody", "4");
        bookWithHolderNameAndTimeOfRent.setNameOfBookHolder("Book Holder Mantas");
        bookWithHolderNameAndTimeOfRent.setTimeOfRent(LocalDate.of(2021, 12, 6));

        listOfBooksWithTakenBookAdded.add(book1);
        listOfBooksWithTakenBookAdded.add(book2);
        listOfBooksWithTakenBookAdded.add(book3);
        listOfBooksWithTakenBookAdded.add(bookWithHolderNameAndTimeOfRent);


        when(bookService.sortedBy(6)).thenReturn(listOfBooksWithTakenBookAdded.stream().sorted((new BookSort())));

        MvcResult mvcResult = mockMvc.perform(get("/books/sort/" + 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(listOfBooksWithTakenBookAdded)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Book> sortedListOfBooksByTakenAndNot = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        String nameOfTheHolderAtIndex0 = sortedListOfBooksByTakenAndNot.get(0).getNameOfBookHolder();
        String nameOfTheHolderAtIndex1 = sortedListOfBooksByTakenAndNot.get(1).getNameOfBookHolder();
        String nameOfTheHolderAtIndex2 = sortedListOfBooksByTakenAndNot.get(2).getNameOfBookHolder();
        String nameOfTheHolderAtIndex3 = sortedListOfBooksByTakenAndNot.get(3).getNameOfBookHolder();

        assertEquals("Book Holder Mantas", nameOfTheHolderAtIndex0);
        assertEquals("default value", nameOfTheHolderAtIndex1);
        assertEquals("default value", nameOfTheHolderAtIndex2);
        assertEquals("default value", nameOfTheHolderAtIndex3);
    }

    @Test
    void shouldRentBookFromLibrary() throws Exception {

        LocalDate timeOfRent = LocalDate.of(2021, 12, 6);

        Book book = new Book(
                LocalDate.now(), "FirstBook", "Horror", "English", "Bob", "1");

        when(bookService.rentBook("1", "Mantas", timeOfRent)).thenReturn(book);

        book.setNameOfBookHolder("Mantas");
        book.setTimeOfRent(timeOfRent);

        MvcResult mvcResult = mockMvc.perform(post("/books/rent/{isbn}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(book))
                        .param("name", "Mantas")
                        .param("timeOfRent", String.valueOf(timeOfRent)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Book bookWithNameOfCurrentHolder = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), Book.class);

        assertEquals("Mantas", bookWithNameOfCurrentHolder.getNameOfBookHolder());
        assertEquals(timeOfRent, bookWithNameOfCurrentHolder.getTimeOfRent());
    }

    @Test
    void deleteBookByIsbnIfExists() throws Exception {

        Book book = new Book(
                LocalDate.now(), "FirstBook", "Horror", "English", "Bob", "1");

        book.setGuid(UUID.randomUUID());

        bookService.createBook(book);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().is2xxSuccessful());

        assertNull(bookService.getBookByGuid(book.getGuid()));

    }

    private String convertObjectToJsonString(Object object) throws JsonProcessingException {
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }

    private List<Book> listOfBooks() {

        return List.of(
                new Book(LocalDate.now(), "SecondBook", "Comedy", "Russian", "Woody", "2"),
                new Book(LocalDate.now(), "FirstBook", "Horror", "English", "Bob", "1"),
                new Book(LocalDate.now(), "ThirdBook", "Adventure", "Lithuanian", "Annto", "3"));
    }

}