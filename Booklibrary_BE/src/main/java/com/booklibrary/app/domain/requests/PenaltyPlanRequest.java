package com.booklibrary.app.domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class PenaltyPlanRequest {

    private String penaltyId;

    private String penaltyType;

    private String basicPenalty;                 //just because You were late

    private String penaltyPerDay;

    private double multiplierCoefficient;      //increases penalty every week
}
