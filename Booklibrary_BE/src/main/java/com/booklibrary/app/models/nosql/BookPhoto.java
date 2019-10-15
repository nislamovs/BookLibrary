package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "BookPhotos")
public class BookPhoto extends AbstractDocument {

    private String bookId;

    private byte[] photo;

    private byte[] icon;

}
