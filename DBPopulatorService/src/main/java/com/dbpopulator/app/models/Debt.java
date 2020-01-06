package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "debt")
public class Debt extends AbstractDocument {

    @Field(name="debtId")
    private String debtId;

    @Field(name="historyId")
    private String historyId;

    @Field(name="bookNumber")
    private String bookNumber;

    @Field(name="visitorId")
    private String visitorId;

    @Field(name="overdueDays")
    private int overdueDays;

    @Field(name="actualPenalty")
    private Money actualPenalty;

    @Field(name="payments")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinProperty(name="payments")
    private List<Payment> payments;

}
