package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Data
@Document(collection = "BookData")
public class BookData extends AbstractDocument {

    private String bookId;

    private String bookTitle;

    private String isbn;

    private String author;

    private String subject;

    private String description;

    private String annotation;

    private LocalDate copyrightDate;

    private String language;

    private int totalCount;

    private int availableCount;

    private int popularityRate;

    private Money internalPrice;
}
