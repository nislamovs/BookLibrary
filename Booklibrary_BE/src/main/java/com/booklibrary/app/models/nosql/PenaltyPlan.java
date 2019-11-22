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
@Document(collection = "penaltyPlans")
public class PenaltyPlan extends AbstractDocument {

    private String penaltyId;

    private PenaltyType penaltyType;

    private Money basicPenalty;                 //just because You were late

    private Money penaltyPerDay;

    private double multiplierCoefficient;      //increases penalty every week

    private enum PenaltyType {LITE, MEDIUM, HARD, CRUEL}
}
