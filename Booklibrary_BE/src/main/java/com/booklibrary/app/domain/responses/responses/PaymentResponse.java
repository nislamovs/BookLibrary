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
public class PaymentResponse {

    private String paymentId;
    private String bookId;
    private String bookNumber;
    private String debtId;
    private String penaltyId;

    private String firstname;
    private String lastname;
    private String bankAccount;
    private String otherInfo;

    private String amount;
    private String transactionNumber;
    private String paymentInfo;
    private byte[] paymentReceiptPhoto;
}
