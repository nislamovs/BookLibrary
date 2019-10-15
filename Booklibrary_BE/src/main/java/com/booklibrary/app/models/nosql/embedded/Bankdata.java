package com.booklibrary.app.models.nosql.embedded;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Bankdata  {

    private String cardNumber;
    private String bankAccount;
    private String cvvCode;
    private String expiryDate;
    private String cardHolder;
}
