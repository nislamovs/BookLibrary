package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookStorageResponse {

    private String bookId;

    private String bookNumber;

    private String isbn;

    private String status;

    private String visitorId;
}
