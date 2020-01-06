package com.dbpopulator.app.services;

import com.dbpopulator.app.models.Payment;
import com.dbpopulator.app.repository.nosql.PaymentRepository;
import com.dbpopulator.app.services.components.CurlScripts;
import com.dbpopulator.app.services.components.ShellScriptExecutor;
import com.dbpopulator.app.services.messaging.SlackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.IOUtils;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private static final String SERVICE_NAME = "PaymentService";
    private final ObjectMapper mapper;
    private final ShellScriptExecutor executor;
    private final CurlScripts scripts;
    private final PaymentRepository paymentRepository;
    private final SlackService slackService;
    private final ProcessingControlService processingControlService;

    @SneakyThrows
    public void populatePayments() {

        slackService.pushMessage(format(SERVICE_NAME + " processing started [%s]", TIMESTAMP()));

        //Total 1500 records
        //Setting firstname, lastname, bankAccount
        List<Payment> paymentDataPart1 = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.paymentScript.split(" ")), Payment[].class));
        List<Payment> paymentDataPart2 = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.paymentScript.split(" ")), Payment[].class));
        List<Payment> paymentData = ListUtils.union(paymentDataPart1, paymentDataPart2);

        //Setting the rest fields
        for (int n = 1 ; n <= 1500; n++ ) {
            paymentData.get(n).setPaymentId(String.valueOf(n));
            paymentData.get(n).setBookId("");
            paymentData.get(n).setBookNumber(String.valueOf(n*5+1));
            paymentData.get(n).setDebtId(String.valueOf(n));
            paymentData.get(n).setPenaltyId(String.valueOf(new Random().nextInt(4) + 1));

            paymentData.get(n).setOtherInfo("");

            paymentData.get(n).setAmount(Money.parse("EUR " + genRandAmount()));
            paymentData.get(n).setTransactionNumber(genRandTransactionNumber());
            paymentData.get(n).setPaymentInfo(genRandPaymentReason());
            paymentData.get(n).setPaymentReceiptPhoto(genPaymentReceipt());
        }

//        System.out.println(paymentData.get(1500).toString());
        paymentRepository.saveAll(paymentData.subList(1, 1501));
        processingControlService.markAsCompleted(ProcessingControlService.TablesList.PAYMENTS);
        slackService.pushMessage(format(SERVICE_NAME + " processing finished [%s]", TIMESTAMP()));
    }

    private String genPaymentReceipt() {
        String filenamePostfix = String.valueOf(new Random().nextInt(5) + 1);
        String filename = "app/static/payment_receipts/payment_receipt_example" + filenamePostfix + ".jpg";
        try {
            return Base64.getEncoder().encodeToString(IOUtils.toByteArray(new FileInputStream(filename)));
        } catch (Exception e) {
            log.info("Filename '{}' not found!", filename);
        }

        return "";
    }

    private String genRandPaymentReason() {
        List<String> reason = new ArrayList<>();
        reason.addAll(ImmutableList.of("I was sick, sorry.", "Forgot about the book I took.",
                "Lost the book and tried to find it all this time long.", "I don't want to have debts in this library :)",
                "I'm so stupid - just paid this invoice", "", "", "", "", "Donations"));

        return reason.get(new Random().nextInt(reason.size()));
    }

    private String genRandTransactionNumber() {
        return UUID.randomUUID().toString().trim().replace("-", "").substring(0, 17);
    }

    private String genRandAmount() {
        val min = 10; val max = 80;
        String euros = String.valueOf(new Random().nextInt((max - min) + 1) + min);
        String cents = String.valueOf(new Random().nextInt(99));
        cents = cents.length() < 2 ? "0" + cents : cents;

        return euros + "." + cents;
    }
}
