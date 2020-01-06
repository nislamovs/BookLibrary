package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Document(collection = "bookStorage")
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AbstractDocument {

    @Field(name="bookId")
    private String bookId;

    @Field(name="bookNumber")
    private String bookNumber;

    @Field(name="isbn")
    private String isbn;

    @Field(name="status")
    private Status status;

    @Field(name="visitorId")
    private String visitorId;

    public static enum Status { RESTORATION, ON_SHELF, LOST, ON_HANDS, NOT_DELIVERED }
}
