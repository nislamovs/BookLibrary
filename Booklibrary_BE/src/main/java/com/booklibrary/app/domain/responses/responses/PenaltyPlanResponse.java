package com.booklibrary.app.domain.responses.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PenaltyPlanResponse {

    private String penaltyId;

    private String penaltyType;

    private String basicPenalty;                 //just because You were late

    private String penaltyPerDay;

    private double multiplierCoefficient;      //increases penalty every week
}
