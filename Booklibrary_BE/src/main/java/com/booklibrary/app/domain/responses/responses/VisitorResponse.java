package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitorResponse {

    private String visitorId;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String phone;
    private String gender;
    private String email;
    private List<String> interests;
    private String category;
    private Address livingAddress;
    private Address deliveryAddress;
    private Bankdata bankData;

    private static class Address {

        private String city;
        private String street;
        private String postalCode;
        private String additionalInfo;
    }

    private static class Bankdata  {

        private String cardNumber;
        private String bankAccount;         //IBAN
        private String cvvCode;
        private String expiryDate;
        private String cardHolder;
    }
}
