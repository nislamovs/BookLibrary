package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.PenaltyPlanNotFoundException;
import com.booklibrary.app.domain.requests.PenaltyPlanRequest;
import com.booklibrary.app.models.nosql.PenaltyPlan;
import com.booklibrary.app.repository.nosql.PenaltyPlanRepository;
import com.booklibrary.app.services.interfaces.IPenaltyPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.booklibrary.app.converters.orikaConverters.ToDocumentConverters.toDocument;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyPlanService implements IPenaltyPlanService {

    private final PenaltyPlanRepository penaltyRepository;

    @Override
    public List<PenaltyPlan> getPenaltyPlans() {
        return penaltyRepository.findAll();
    }

    @Override
    public PenaltyPlan getPenaltyPlanById(String penaltyId) {
        return penaltyRepository.findByPlanId(penaltyId)
            .orElseThrow(() -> new PenaltyPlanNotFoundException(format("Penalty plan with Id [%s] was not found.", penaltyId)));
    }

    @Override
    public PenaltyPlan addPenaltyPlan(PenaltyPlanRequest penaltyPlan) {
        return penaltyRepository.save(toDocument(penaltyPlan));
    }

    @Override
    public PenaltyPlan updatePenaltyPlan(PenaltyPlanRequest penaltyPlan) {
        return penaltyRepository.save(toDocument(penaltyPlan));
    }

    @Override
    public void deletePenaltyPlan(String penaltyId) {
        penaltyRepository.deleteByPenaltyId(penaltyId);
    }

}
