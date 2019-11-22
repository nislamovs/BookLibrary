package com.booklibrary.app.configuration.changelogs.V1.actions;

import com.booklibrary.app.models.nosql.Book;

import java.util.ArrayList;
import java.util.List;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V1/resources/book/ALL_BOOKS.yml")
//@ConfigurationProperties
//@Configuration
//@Slf4j
public class PresetBookCollection {

    //TODO set [holderId]

    private static List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books) {
        PresetBookCollection.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public static List<Book> presetBookCollection() {
        return books;
    }
}
