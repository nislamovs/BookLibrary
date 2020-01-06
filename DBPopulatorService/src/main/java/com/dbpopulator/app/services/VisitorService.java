package com.dbpopulator.app.services;

import com.dbpopulator.app.domain.exceptions.DebtNotFoundException;
import com.dbpopulator.app.domain.exceptions.HistoryRecordNotFoundException;
import com.dbpopulator.app.domain.exceptions.PaymentNotFoundException;
import com.dbpopulator.app.models.Debt;
import com.dbpopulator.app.models.History;
import com.dbpopulator.app.models.Payment;
import com.dbpopulator.app.models.VisitorData;
import com.dbpopulator.app.models.embedded.Address;
import com.dbpopulator.app.models.embedded.Bankdata;
import com.dbpopulator.app.repository.nosql.*;
import com.dbpopulator.app.services.components.CurlScripts;
import com.dbpopulator.app.services.components.ShellScriptExecutor;
import com.dbpopulator.app.services.messaging.SlackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitorService {

    private static final String SERVICE_NAME = "VisitorDataService";

    private final ObjectMapper mapper;
    private final ShellScriptExecutor executor;
    private final CurlScripts scripts;
    private final VisitorDataRepository visitorDataRepository;
    private final DebtRepository debtRepository;
    private final PaymentRepository paymentRepository;
    private final HistoryRepository historyRepository;
    private final SlackService slackService;
    private final ProcessingControlService processingControlService;

    @SneakyThrows
    public void populateVisitorData() {

        if (!processingControlService.checkIfProcessed(ProcessingControlService.TablesList.PAYMENTS.getTableName())) {
            slackService.pushMessage(format(SERVICE_NAME + " processing canceled - payments table was not processed! [%s]", TIMESTAMP()));
            return;
        }

        if (!processingControlService.checkIfProcessed(ProcessingControlService.TablesList.DEBTS.getTableName())) {
            slackService.pushMessage(format(SERVICE_NAME + " processing canceled - debts table was not processed! [%s]", TIMESTAMP()));
            return;
        }

        slackService.pushMessage(format(SERVICE_NAME + " processing started [%s]", TIMESTAMP()));

        List<VisitorData> visitors = genVisitorPersData();

        List<Address> visitorLivingAddresses = genVisitorAddresses();
        List<Address> visitorDeliveryAddresses = genVisitorAddresses();
        List<Bankdata> visitorBankdata = genVisitorBankData();

        for (int n = 1; n < 8000; n++) {

            visitors.get(n).setVisitorId(String.valueOf(n));
            //firstname
            //lastname
            //birthdate
            //phone
            //gender
            visitors.get(n).setGender(VisitorData.Gender.valueOf(visitors.get(n).getGender().toString().toUpperCase()));
            //email
            visitors.get(n).setInterests(genVisitorInterests());
            visitors.get(n).setCategory(genVisitorCategory());
            visitors.get(n).setCategory(genVisitorCategory());
            visitors.get(n).setLivingAddress(visitorLivingAddresses.get(n));
            visitors.get(n).setDeliveryAddress(visitorDeliveryAddresses.get(n));
            if ( n <= 2500) {
                visitors.get(n).setHistory(Arrays.asList(historyRetrieve(n)));
//                visitors.get(n).setPayments(Arrays.asList(paymentRetrieve(n)));
            }

            Bankdata bankdata = visitorBankdata.get(n);
            bankdata.setCardHolder(visitors.get(n).getFirstname() + " " + visitors.get(n).getLastname());
            visitors.get(n).setBankData(bankdata);
        }

        visitorDataRepository.saveAll(visitors.subList(1,8000));
        processingControlService.markAsCompleted(ProcessingControlService.TablesList.VISITORS);
        slackService.pushMessage(format(SERVICE_NAME + " processing finished [%s]", TIMESTAMP()));
    }

    @SneakyThrows
    private List<VisitorData> genVisitorPersData() {
        List<VisitorData> visitorPersData = new ArrayList<>();
        for (int n = 1; n <= 8; n++) {
            List<VisitorData> visitorDataPart = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.visitorPersDataScript.split(" ")), VisitorData[].class));
            visitorPersData = ListUtils.union(visitorPersData, visitorDataPart);
        }
        return visitorPersData;
    }

    @SneakyThrows
    private List<Bankdata> genVisitorBankData() {
        List<Bankdata> bankData = new ArrayList<>();
        for (int n = 1; n <= 8; n++) {
            List<Bankdata> bankDataPart = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.visitorBankDataScript.split(" ")), Bankdata[].class));
            bankData = ListUtils.union(bankData, bankDataPart);
        }
        return bankData;
    }

    private History historyRetrieve(int historyId) {
        return historyRepository.findByHistoryId(String.valueOf(historyId))
                .orElseThrow(() -> new HistoryRecordNotFoundException(format("History record with Id [%s] was not found.", historyId)));
    }

//    private Payment paymentRetrieve(int paymentId) {
//        return paymentRepository.findByPaymentId(String.valueOf(paymentId))
//                .orElseThrow(() -> new PaymentNotFoundException(format("Payment with Id [%s] was not found.", paymentId)));
//    }
//
//    private Debt debtRetrieve(int debtId) {
//        if (debtId <= 1500) {
//            return debtRepository.findByDebtId(String.valueOf(debtId))
//                    .orElseThrow(() -> new DebtNotFoundException(format("Debt with Id [%s] was not found.", debtId)));
//        }
//
//        return null;
//    }

    private List<VisitorData.Interests> genVisitorInterests() {
        List<String> interests = new ArrayList<>();
        interests.addAll(ImmutableList.of("BIOLOGY","CHEMISTRY","COMPUTER_SCIENCE","ECONOMICS", "PSYCHOLOGY",
                "ENGINEERING","MANAGEMENT","MATERIAL_SCIENCE","MATHEMATICS","MEDICINE","MICROBIOLOGY",
                "PHARMACOLOGY","SOCIAL_SCIENCE","TOXICOLOGY","VETERINARY", "PHYSICS"));

        List<VisitorData.Interests> visitorInterests = new ArrayList<>();
        int interestsCount = new Random().nextInt(interests.size());
        for (int n = 0; n < interestsCount; n++) {
            visitorInterests.add(
                    VisitorData.Interests.valueOf(
                            interests.get(new Random().nextInt(interests.size() - 1))
                    )
            );
        }

        return visitorInterests.stream().distinct().collect(Collectors.toList());
    }

    private VisitorData.Category genVisitorCategory() {
        List<String> visitorCategories = new ArrayList<>();
        visitorCategories.addAll(ImmutableList.of("STUDENT","ENGINEER","RETIRED","WANTED","UNEMPLOYED","OTHER"));

        return VisitorData.Category.valueOf(visitorCategories.get(new Random().nextInt(visitorCategories.size())));
    }

    @SneakyThrows
    private List<Address>  genVisitorAddresses() {
        List<Address> visitorDataAddresses = new ArrayList<>();
        for (int n = 1; n <= 8; n++) {
            List<Address> visitorDataAddressPart = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.visitorAddressDataScript.split(" ")), Address[].class));
            visitorDataAddresses = ListUtils.union(visitorDataAddresses, visitorDataAddressPart);
        }
        return visitorDataAddresses;
    }
}
