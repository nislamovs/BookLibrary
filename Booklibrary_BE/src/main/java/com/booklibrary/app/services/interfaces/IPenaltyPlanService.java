package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.PenaltyPlanRequest;
import com.booklibrary.app.models.nosql.PenaltyPlan;

import java.util.List;

public interface IPenaltyPlanService {

    List<PenaltyPlan> getPenaltyPlans();

    PenaltyPlan getPenaltyPlanById(String penaltyId);

    PenaltyPlan addPenaltyPlan(PenaltyPlanRequest penaltyPlan);

    PenaltyPlan updatePenaltyPlan(PenaltyPlanRequest penaltyPlan);

    void deletePenaltyPlan(String penaltyId);

}
