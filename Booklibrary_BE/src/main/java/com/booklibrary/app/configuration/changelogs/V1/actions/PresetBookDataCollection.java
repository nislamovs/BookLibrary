package com.booklibrary.app.configuration.changelogs.V1.actions;

import com.booklibrary.app.models.nosql.BookData;
import com.booklibrary.app.models.nosql.BookPhoto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.booklibrary.app.configuration.changelogs.V1.actions.PresetBookImageCollection.presetBookImageCollection;
import static java.util.Comparator.comparing;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V1/resources/bookdata/BIOLOGY.yml")
//@ConfigurationProperties
//@Configuration
//@Slf4j
public class PresetBookDataCollection {

    //TODO put all yml file paths to @PropertySource (use @PropertySources annotation)

    private static List<BookData> books = new ArrayList<>();
    private static List<BookPhoto> bookImageStorage = new ArrayList<>();

    public void setBooks(List<BookData> books) {
        PresetBookDataCollection.books = books;
    }

    public List<BookData> getBooks() {
        return books;
    }

    public static List<BookData> presetBookDataCollection() {

        bookImageStorage = presetBookImageCollection();

        books.sort(comparing(BookData::getIsbn));
        bookImageStorage.sort(comparing(BookPhoto::getIsbn));

        Iterator<BookData> bookIterator = books.iterator();
        Iterator<BookPhoto> bookImageIterator = bookImageStorage.iterator();

        while (bookIterator.hasNext() && bookImageIterator.hasNext()) {
            bookIterator.next().setBookPhoto(bookImageIterator.next());
        }

        return books;
    }
}
