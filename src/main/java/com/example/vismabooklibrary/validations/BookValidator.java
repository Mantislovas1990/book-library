package com.example.vismabooklibrary.validations;

import com.example.vismabooklibrary.entities.Book;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookValidator {

    public static boolean checkBookAmount(List<Book> list, String name) {
        List<Book> amountOfBooks = new ArrayList<>();

        for (Book b : list) {
            if (b.getNameOfBookHolder().equals(name))
                amountOfBooks.add(b);
        }

        return amountOfBooks.size() <= 2;
    }

    public static boolean checkDays(LocalDate date) {

        LocalDate localDate = LocalDate.now();

        long days = ChronoUnit.DAYS.between(localDate, date);

        return days <= 60;
    }
}
