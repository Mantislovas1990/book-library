package com.example.vismabooklibrary.util;

import com.example.vismabooklibrary.entities.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParse {

    private JsonParse() {
    }

    public static Book AddObjectToJsonSaveNewBook(Book object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            File f = new File("Books.json");
            if (!f.exists()) {
                mapper.writeValue(f, object);
            } else {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                List<Book> temp = mapper.readValue(new File("Books.json"), new TypeReference<>() {
                });
                temp.add(object);
                mapper.writeValue(f, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void DeleteJsonFile() {
        try {
            File file = new File("Books.json");
            boolean delete = file.delete();
            if (delete) {
                System.out.println("File is deleted");
            } else {
                System.out.println("File is not deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Book> ParsJsonToObjectGetAvailableBooks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(new File("Books.json"), new TypeReference<>() {
        });
    }

}
