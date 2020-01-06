package com.dbpopulator.app.services;


import com.dbpopulator.app.domain.exceptions.PaymentNotFoundException;
import com.dbpopulator.app.models.Debt;
import com.dbpopulator.app.models.Payment;
import com.dbpopulator.app.repository.nosql.DebtRepository;
import com.dbpopulator.app.repository.nosql.PaymentRepository;
import com.dbpopulator.app.services.messaging.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.util.Arrays.asList;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebtService {

    private static final String SERVICE_NAME = "DebtService";
    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;
    private final SlackService slackService;
    private final ProcessingControlService processingControlService;

    @SneakyThrows
    public void populateDebts() {

        if (!processingControlService.checkIfProcessed(ProcessingControlService.TablesList.PAYMENTS.getTableName())) {
            slackService.pushMessage(format(SERVICE_NAME + " processing canceled - payments table was not processed! [%s]", TIMESTAMP()));
            return;
        }

        slackService.pushMessage(format(SERVICE_NAME + " processing started [%s]", TIMESTAMP()));
        List<Debt> debts = new ArrayList<>();

        for (int n = 1; n <= 1500; n++ ) {

            Payment payment = paymentRetrieve(n);
//            slackService.pushMessage(payment.toString());
            Debt debt = Debt.builder()
                    .debtId(String.valueOf(n))
                    .bookNumber(String.valueOf(n*5+1))
                    .visitorId(String.valueOf(n))
                    .historyId(String.valueOf(n))
                    .overdueDays(new Random().nextInt(15)+1)
                    .payments(asList(payment))
                    .actualPenalty(payment.getAmount())
                    .build();

            debts.add(debt);
        }
        debtRepository.saveAll(debts);
        processingControlService.markAsCompleted(ProcessingControlService.TablesList.DEBTS);
        slackService.pushMessage(format(SERVICE_NAME + " processing finished [%s]", TIMESTAMP()));
    }

    private Payment paymentRetrieve(int paymentId) {
        return paymentRepository.findByPaymentId(String.valueOf(paymentId))
                .orElseThrow(() -> new PaymentNotFoundException(format("Payment with Id [%s] was not found.", paymentId)));
    }
}
