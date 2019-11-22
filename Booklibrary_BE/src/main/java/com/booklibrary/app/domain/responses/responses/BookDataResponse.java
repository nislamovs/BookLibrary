package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDataResponse {

    private String bookId;

    private String bookTitle;

    private String isbn;

    private String publisher;

    private List<String> authors;

    private List<String> categories;

    private String searchInfo;

    private String annotation;

    private int pages;

    private LocalDate publishedDate;

    private String language;

    private int totalCount;

    private int availableCount;

    private BigDecimal popularityRate;

    private String internalPrice;

    private byte[] bookPhoto;
}
