package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IPenaltyController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.PenaltyPlanRequest;
import com.booklibrary.app.services.PenaltyPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.from;
import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.toResponse;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PenaltyController implements IPenaltyController {

    private final PenaltyPlanService penaltyService;

    @GetMapping("/penalty")
    public ResponseEntity<?> getAllPenalties() {
        log.info("Retrieving penalty list.");
        return ok(penaltyService.getPenaltyPlans().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/penalty/{penaltyId}")
    public ResponseEntity<?> getPenaltyPlanById(@PathVariable("penaltyId") @NotBlank String penaltyId) {
        log.info("Retrieving penalty plan by Id: {}", penaltyId);
        return ok(toResponse(penaltyService.getPenaltyPlanById(penaltyId)));
    }

    @PostMapping("/penalty")
    public ResponseEntity<?> addPenaltyPlan(@Valid @RequestBody PenaltyPlanRequest penaltyPlanRequest) {
        log.info("Saving new penalty plan.");
        return ok(from(penaltyService.addPenaltyPlan(penaltyPlanRequest)));
    }

    @PutMapping("/penalty")
    public ResponseEntity<?> updatePenaltyPlan(@Valid @RequestBody PenaltyPlanRequest penaltyPlanRequest) {
        log.info("Updating penalty plan by id {}.", penaltyPlanRequest.getPenaltyId());
        return ok(from(penaltyService.updatePenaltyPlan(penaltyPlanRequest)));
    }

    @DeleteMapping("/penalty/{penaltyId}")
    public ResponseEntity<?> deletePenaltyPlan(@PathVariable("penaltyId") @NotBlank String penaltyId) {
        log.info("Deleting penalty plan by Id : {}.", penaltyId);
        penaltyService.deletePenaltyPlan(penaltyId);
        return (ResponseEntity<?>) ok();
    }
}
