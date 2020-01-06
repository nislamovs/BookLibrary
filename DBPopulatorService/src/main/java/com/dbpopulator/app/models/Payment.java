package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="payments", value="payments")
public class Payment extends AbstractDocument {

    @Field(name="paymentId")
    private String paymentId;

    @Field(name="bookId")
    private String bookId;

    @Field(name="bookNumber")
    private String bookNumber;

    @Field(name="debtId")
    private String debtId;

    @Field(name="penaltyId")
    private String penaltyId;

    @Field(name="firstname")
    private String firstname;

    @Field(name="lastname")
    private String lastname;

    @Field(name="bankAccount")
    private String bankAccount;

    @Field(name="otherInfo")
    private String otherInfo;

    @Field(name="amount")
    private Money amount;

    @Field(name="transactionNumber")
    private String transactionNumber;

    @Field(name="paymentInfo")
    private String paymentInfo;

    @Field(name="paymentReceiptPhoto")
    private String paymentReceiptPhoto;

}
