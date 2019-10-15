package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Data
@Document(collection = "Debts")
public class Debt extends AbstractDocument {

    private String bookId;

    private String bookNumber;

    private String userId;

    private int overdueDays;

    private Money actualPenalty;

    private List<Payment> payments;

}
