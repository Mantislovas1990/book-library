package com.example.vismabooklibrary.util;

import com.example.vismabooklibrary.entities.Book;

import java.util.Comparator;

public class BookSort implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {

        if (o1.getNameOfBookHolder().equals("default value") && o2.getNameOfBookHolder().equals("default value")) {
            return 0;
        }
        if (o1.getNameOfBookHolder().equals("default value") && !o2.getNameOfBookHolder().equals("default value")) {
            return 1;
        }
        return -1;
    }
}
