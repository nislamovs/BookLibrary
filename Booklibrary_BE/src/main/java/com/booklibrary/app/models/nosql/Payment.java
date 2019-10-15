package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "Payments")
public class Payment extends AbstractDocument {

    private String bookId;

    private String bookNumber;

    private String firstname;
    private String lastname;
    private String bankAccount;
    private String otherInfo;

    private String debtId;

    private String transactionNumber;

    private Money amount;

    private String paymentInfo;

    private byte[] paymentReceiptPhoto;

}
