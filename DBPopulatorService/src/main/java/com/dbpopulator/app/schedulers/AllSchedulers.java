package com.dbpopulator.app.schedulers;

import com.dbpopulator.app.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class AllSchedulers {

    private final ProcessingControlService processingControlService;

    private final PaymentService paymentService;
    private final PenaltyPlanService penaltyPlanService;
    private final HistoryService historyService;
    private final DebtService debtService;
    private final VisitorService visitorService;
    private final BookDataService bookDataService;

    @Scheduled(cron = "0 0 */4 * * *")    //every 4 hours
    public void BookPreprocessingScheduler() {
        System.out.println("Scheduler launched - BookPreprocessingScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.BOOKDATA.getTableName()))
            bookDataService.preprocessBookData();
    }

    @Scheduled(cron = "0 0/30 * * * *")    //every 30 mins
    public void BookProcessingScheduler() {
        System.out.println("Scheduler launched - BookProcessingScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.BOOKDATA.getTableName()))
            return;
        bookDataService.populateBookData();
    }

    @Scheduled(cron = "0 0 */3 * * *")    //every 3 hours
    public void PaymentsScheduler() {
        System.out.println("Scheduler launched - PaymentsScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.PAYMENTS.getTableName()))
            return;
        paymentService.populatePayments();
    }

    @Scheduled(cron = "0 0 */3 * * *")    //every 3 hours
    public void DebtsScheduler() {
        System.out.println("Scheduler launched - DebtsScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.DEBTS.getTableName()))
            return;
        debtService.populateDebts();
    }

    @Scheduled(cron = "0 0 */3 * * *")    //every 3 hours
    public void HistoryScheduler() {
        System.out.println("Scheduler launched - HistoryScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.HISTOTY.getTableName()))
            return;
        historyService.populateHistory();
    }

    @Scheduled(cron = "0 0 */3 * * *")    //every 3 hours
    public void PenaltyPlanScheduler() {
        System.out.println("Scheduler launched - PenaltyPlanScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.PENALTYPLANS.getTableName()))
            return;
        penaltyPlanService.populatePenaltyPlans();
    }

    @Scheduled(cron = "0 0 */3 * * *")    //every 3 hours
    public void VisitorScheduler() {
        System.out.println("Scheduler launched - VisitorScheduler");
        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.VISITORS.getTableName()))
            return;
        visitorService.populateVisitorData();
    }
}
