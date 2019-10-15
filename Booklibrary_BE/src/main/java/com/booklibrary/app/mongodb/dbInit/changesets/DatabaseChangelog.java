package com.booklibrary.app.mongodb.dbInit.changesets;

import com.booklibrary.app.models.nosql.Book;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@ChangeLog(order = "001")
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "id01", author = "Nizami Islamovs")
    public void imports(MongoTemplate repo) {
        List<Book> books = Arrays.asList(
            Book.builder().id(UUID.randomUUID().toString()).bookTitle("")
                .build(),
            Book.builder()
                .build(),
            Book.builder()
                .build(),
            Book.builder()
                .build());
        books.forEach(repo::save);
    }
}
