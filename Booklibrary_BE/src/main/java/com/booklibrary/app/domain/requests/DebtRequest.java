package com.booklibrary.app.domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class DebtRequest {

    private String debtId;

    private String historyId;

    private String bookNumber;

    private String visitorId;

    private int overdueDays;

    private String actualPenalty;

    private List<PaymentRequest> payments;
}
