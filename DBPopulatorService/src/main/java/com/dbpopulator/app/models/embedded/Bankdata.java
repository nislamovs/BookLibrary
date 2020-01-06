package com.dbpopulator.app.models.embedded;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Bankdata  {

    private String cardNumber;
    private String bankAccount;         //IBAN
    private String cvvCode;
    private String expiryDate;
    private String cardHolder;
}
