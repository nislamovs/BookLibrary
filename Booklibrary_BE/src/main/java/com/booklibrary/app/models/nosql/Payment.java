package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="payments", value="payments")
public class Payment extends AbstractDocument {

    private String paymentId;
    private String bookId;
    private String bookNumber;
    private String debtId;
    private String penaltyId;

    private String firstname;
    private String lastname;
    private String bankAccount;
    private String otherInfo;

    private Money amount;
    private String transactionNumber;
    private String paymentInfo;
    private String paymentReceiptPhoto;

}
