package com.dbpopulator.app.services;


import com.dbpopulator.app.models.PenaltyPlan;
import com.dbpopulator.app.repository.nosql.PenaltyPlanRepository;
import com.dbpopulator.app.repository.nosql.ProcessingControlRepository;
import com.dbpopulator.app.services.messaging.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class PenaltyPlanService {

    private static final String SERVICE_NAME = "PenaltyPlansService";
    private final PenaltyPlanRepository penaltyPlanRepository;
    private final ProcessingControlService processingControlService;
    private final SlackService slackService;

    @SneakyThrows
    public void populatePenaltyPlans() {
        slackService.pushMessage(format(SERVICE_NAME + " processing started [%s]", TIMESTAMP()));
        List<PenaltyPlan> penalties = new ArrayList<>();

        penalties.add(new PenaltyPlan("1", PenaltyPlan.PenaltyType.LITE, Money.of(5, "EUR"), Money.of(1.00, "EUR"), 1.0));
        penalties.add(new PenaltyPlan("2", PenaltyPlan.PenaltyType.MEDIUM, Money.of(10,  "EUR"), Money.of(2.00, "EUR"), 1.1));
        penalties.add(new PenaltyPlan("3", PenaltyPlan.PenaltyType.HARD, Money.of(15, "EUR"), Money.of(3.00, "EUR"), 1.3));
        penalties.add(new PenaltyPlan("4", PenaltyPlan.PenaltyType.CRUEL, Money.of(20, "EUR"), Money.of(5.00, "EUR"), 1.5));

        penaltyPlanRepository.saveAll(penalties);
        processingControlService.markAsCompleted(ProcessingControlService.TablesList.PENALTYPLANS);
        slackService.pushMessage(format(SERVICE_NAME + " processing finished [%s]", TIMESTAMP()));
    }

}
