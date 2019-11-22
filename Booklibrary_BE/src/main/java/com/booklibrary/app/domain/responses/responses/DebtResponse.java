package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebtResponse {

    private String debtId;

    private String historyId;

    private String bookNumber;

    private String visitorId;

    private int overdueDays;

    private String actualPenalty;

    private List<PaymentResponse> payments;
}
