package com.booklibrary.app.models.nosql;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Data
@Document(collection = "PenaltyPlans")
public class PenaltyPlan extends AbstractDocument {

    private String planId;

    private PenaltyType name;

    private Money basicPenalty;             //just because You were late

    private Money penaltyPerDay;

    private int multiplierCoefficient;      //increases every week

    private enum PenaltyType {LITE, MEDIUM, HARD, CRUEL}
}
