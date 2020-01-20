package com.dbpopulator.app.schedulers;

import com.dbpopulator.app.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class AllSchedulers {

    private final ProcessingControlService processingControlService;

    private final PaymentService paymentService;
    private final PenaltyPlanService penaltyPlanService;
    private final HistoryService historyService;
    private final DebtService debtService;
    private final VisitorService visitorService;
    private final BookDataService bookDataService;

//    @Scheduled(cron = "*/5 * * * * *")    //every 5 sec
//    public void TestSchedulers() {
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>        TESTING SCHEDULERS         <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//    }
//
//    @Scheduled(cron = "* * */4 * * *")    //every 4 hours
//    public void bookPreprocessingScheduler() {
//        System.out.println("Scheduler launched - BookPreprocessingScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.BOOKDATA.getTableName()))
//            bookDataService.preprocessBookData();
//    }
//
//    @Scheduled(cron = "* */30 * * * *")    //every 30 mins
//    public void bookProcessingScheduler() {
//        System.out.println("Scheduler launched - BookProcessingScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.BOOKDATA.getTableName()))
//            return;
//        bookDataService.populateBookData();
//    }
//
//    @Scheduled(cron = "* * */3 * * *")    //every 3 hours
//    public void paymentsScheduler() {
//        System.out.println("Scheduler launched - PaymentsScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.PAYMENTS.getTableName()))
//            return;
//        paymentService.populatePayments();
//    }
//
//    @Scheduled(cron = "* * */3 * * *")    //every 3 hours
//    public void debtsScheduler() {
//        System.out.println("Scheduler launched - DebtsScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.DEBTS.getTableName()))
//            return;
//        debtService.populateDebts();
//    }
//
//    @Scheduled(cron = "* * */3 * * *")    //every 3 hours
//    public void historyScheduler() {
//        System.out.println("Scheduler launched - HistoryScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.HISTOTY.getTableName()))
//            return;
//        historyService.populateHistory();
//    }
//
//    @Scheduled(cron = "* * */3 * * *")    //every 3 hours
//    public void penaltyPlanScheduler() {
//        System.out.println("Scheduler launched - PenaltyPlanScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.PENALTYPLANS.getTableName()))
//            return;
//        penaltyPlanService.populatePenaltyPlans();
//    }
//
//    @Scheduled(cron = "* * */3 * * *")    //every 3 hours
//    public void visitorScheduler() {
//        System.out.println("Scheduler launched - VisitorScheduler");
//        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.VISITORS.getTableName()))
//            return;
//        visitorService.populateVisitorData();
//    }
}

