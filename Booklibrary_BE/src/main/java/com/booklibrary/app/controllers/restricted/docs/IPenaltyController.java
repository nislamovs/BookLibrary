package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.PenaltyPlanRequest;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;

@Api(tags = { "Penalty plans operations" })
public interface IPenaltyController {

    ResponseEntity<?> getAllPenalties();

    ResponseEntity<?> getPenaltyPlanById(String penaltyId);

    ResponseEntity<?> addPenaltyPlan(PenaltyPlanRequest penaltyPlan);

    ResponseEntity<?> updatePenaltyPlan(PenaltyPlanRequest penaltyPlan);

    ResponseEntity<?> deletePenaltyPlan(String penaltyId);
}
