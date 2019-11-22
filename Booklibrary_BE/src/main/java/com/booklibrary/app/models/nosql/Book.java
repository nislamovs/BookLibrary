package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Document(collection = "bookStorage")
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AbstractDocument {

    private String bookId;

    private String bookNumber;

    private String isbn;

    private Status status;

    private String visitorId;

    public static enum Status { RESTORATION, ON_SHELF, LOST, ON_HANDS, NOT_DELIVERED }
}
