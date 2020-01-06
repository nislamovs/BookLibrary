package com.dbpopulator.app.domain.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookAPIResponse {

    @JsonProperty("amount")
    public Double amount;
    @JsonProperty("currencyCode")
    public String currencyCode;
}
