package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "penaltyPlans")
public class PenaltyPlan extends AbstractDocument {

    @Field(name="penaltyId")
    private String penaltyId;

    @Field(name="penaltyType")
    private PenaltyType penaltyType;

    @Field(name="basicPenalty")
    private Money basicPenalty;                 //just because You were late

    @Field(name="penaltyPerDay")
    private Money penaltyPerDay;

    @Field(name="multiplierCoefficient")
    private double multiplierCoefficient;      //increases penalty every week

    public static enum PenaltyType {LITE, MEDIUM, HARD, CRUEL}
}
