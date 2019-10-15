package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Builder
@Data
@Document(collection = "BookStorage")
public class Book extends AbstractDocument {

    private String bookId;

    @Size(max = 8, min = 8)
    private String bookNumber;

    private String status;

    private String holder;

}
