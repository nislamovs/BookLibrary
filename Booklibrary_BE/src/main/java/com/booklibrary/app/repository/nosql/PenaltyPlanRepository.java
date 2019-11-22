package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.PenaltyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenaltyPlanRepository extends MongoRepository<PenaltyPlan, String> {

    Optional<PenaltyPlan> findByPlanId(String planId);

    void deleteByPenaltyId(String penaltyId);
}
