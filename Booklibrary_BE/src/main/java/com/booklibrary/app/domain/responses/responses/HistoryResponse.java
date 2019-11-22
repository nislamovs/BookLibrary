package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse {

    private String historyId;

    private String visitorId;

    private String bookNumber;

    private LocalDate bookPickDate;

    private LocalDate bookExpectedReturnDate;

    private LocalDate bookActualReturnDate;

    private Boolean isFailedBookReturn;

    private DebtResponse debt;

    private int feedback;

    private String comment;
}
