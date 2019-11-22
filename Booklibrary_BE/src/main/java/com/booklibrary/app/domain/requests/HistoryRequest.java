package com.booklibrary.app.domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class HistoryRequest {

    private String historyId;

    private String visitorId;

    private String bookNumber;

    private LocalDate bookPickDate;

    private LocalDate bookExpectedReturnDate;

    private LocalDate bookActualReturnDate;

    private Boolean isFailedBookReturn;

    private DebtRequest debt;

    private int feedback;

    private String comment;
}
