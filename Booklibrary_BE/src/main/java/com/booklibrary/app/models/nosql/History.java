package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Data
@Document(collection = "History")
public class History extends AbstractDocument {

    private String userId;          //Link

    private String bookId;          //link

    private String bookNumber;      //link

    private LocalDate bookPickDate;

    private LocalDate bookExpectedReturnDate;

    private LocalDate bookActualReturnDate;

    private boolean isFailedBookReturn;

    private Debt debt;

    private int feedback;

    private String comment;

}
