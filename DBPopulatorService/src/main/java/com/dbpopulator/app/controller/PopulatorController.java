package com.dbpopulator.app.controller;


import com.dbpopulator.app.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

import static com.dbpopulator.app.services.ProcessingControlService.TablesList;
import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class PopulatorController {

    private final ProcessingControlService processingControlService;

    private final PaymentService paymentService;
    private final PenaltyPlanService penaltyPlanService;
    private final HistoryService historyService;
    private final DebtService debtService;
    private final VisitorService visitorService;
    private final BookDataService bookDataService;

    @GetMapping("/trigger")
    public ResponseEntity<?> triggerPopulationService(@Valid @RequestParam(value = "service", required = true) @NotBlank String serviceToTrigger) {

        processingControlService.validate(serviceToTrigger);

        if (ProcessingControlService.TablesList.BOOKDATA.getTableName().equalsIgnoreCase(serviceToTrigger)
                || ProcessingControlService.TablesList.BOOKSTORAGE.getTableName().equalsIgnoreCase(serviceToTrigger)
                || ProcessingControlService.TablesList.BOOKPHOTO.getTableName().equalsIgnoreCase(serviceToTrigger)) {

            bookDataService.populateBookData();
        }

//Not necessary, because all book processing is implementer inside populateBookData() method.
//        if (ProcessingControlService.TablesList.BOOKSTORAGE.getTableName().equalsIgnoreCase(serviceToTrigger)) paymentService.populatePayments();
//        if (ProcessingControlService.TablesList.BOOKPHOTO.getTableName().equalsIgnoreCase(serviceToTrigger)) paymentService.populatePayments();

        if (TablesList.PAYMENTS.getTableName().equalsIgnoreCase(serviceToTrigger)) paymentService.populatePayments();
        if (TablesList.DEBTS.getTableName().equalsIgnoreCase(serviceToTrigger)) debtService.populateDebts();
        if (TablesList.HISTOTY.getTableName().equalsIgnoreCase(serviceToTrigger)) historyService.populateHistory();

        if (TablesList.PENALTYPLANS.getTableName().equalsIgnoreCase(serviceToTrigger))
            penaltyPlanService.populatePenaltyPlans();
        if (TablesList.VISITORS.getTableName().equalsIgnoreCase(serviceToTrigger)) visitorService.populateVisitorData();

        return ok(TIMESTAMP());
    }

    @GetMapping("/trigger/help")
    public ResponseEntity<?> triggerHelp() {
        Map<String, String> services = new HashMap<>();
        services.put("Available data population services", TablesList.asString());
        return ok(services);
    }

    @GetMapping("/trigger/status")
    public ResponseEntity<?> triggerStatus() {
        return ok(processingControlService.retrieveTotalStatusInfo());
    }
}
