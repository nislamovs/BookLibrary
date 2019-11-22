package com.booklibrary.app.configuration.changelogs.V1.actions;

import com.booklibrary.app.models.nosql.BookPhoto;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V1/resources/bookimages/ALL_BOOK_IMAGES.yml")
//@ConfigurationProperties
//@Configuration
@Slf4j
public class PresetBookImageCollection {

    private static List<BookPhoto> bookImageStorage = new ArrayList<>();

    public void setBookImageStorage(List<BookPhoto> bookImageStorage) {
        PresetBookImageCollection.bookImageStorage = bookImageStorage;
    }

    public List<BookPhoto> getBookImageStorage() {
        return bookImageStorage;
    }

    public static List<BookPhoto> presetBookImageCollection() {
        for (var bookPhoto : bookImageStorage) {
            String imageFilename = "";
            try {
                imageFilename = "src/main/java/com/booklibrary/app/configuration/changelogs/V1/resources/bookimages/photos/" + bookPhoto.getIsbn() + ".jpeg";
                bookPhoto.setBookPhoto(Base64.getEncoder().encodeToString(IOUtils.toByteArray(new FileInputStream(imageFilename))));
            } catch (Exception e) {
                log.info("Filename '{}' not found!", imageFilename);
            }

        }
        return bookImageStorage;
    }
}
