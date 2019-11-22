package com.booklibrary.app.domain.responses.ackResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookCreatedResponse {

    private String bookTitle;

    private int totalBooks;

    private String isbn;

    private String bookId;

    private Instant createdDate;

    private String createdBy;
}
