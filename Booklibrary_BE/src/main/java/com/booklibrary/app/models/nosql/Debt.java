package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "debt")
public class Debt extends AbstractDocument {

    private String debtId;

    private String historyId;

    private String bookNumber;

    private String visitorId;

    private int overdueDays;

    private Money actualPenalty;

    @OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval = true)
    @JoinProperty(name="payments")
    private List<Payment> payments;

}
