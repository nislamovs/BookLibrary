package com.dbpopulator.app.services;

import com.dbpopulator.app.domain.exceptions.DebtNotFoundException;
import com.dbpopulator.app.models.Debt;
import com.dbpopulator.app.models.History;
import com.dbpopulator.app.models.Payment;
import com.dbpopulator.app.repository.nosql.DebtRepository;
import com.dbpopulator.app.repository.nosql.HistoryRepository;
import com.dbpopulator.app.services.components.CurlScripts;
import com.dbpopulator.app.services.components.ShellScriptExecutor;
import com.dbpopulator.app.services.messaging.SlackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.utility.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.time.DateUtils;
import org.javamoney.moneta.Money;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.util.stream.Stream.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {

    private static final String SERVICE_NAME = "HistoryService";
    private final ObjectMapper mapper;
    private final ShellScriptExecutor executor;
    private final CurlScripts scripts;
    private final HistoryRepository historyRepository;
    private final DebtRepository debtRepository;
    private final SlackService slackService;
    private final ProcessingControlService processingControlService;

    @SneakyThrows
    public void populateHistory() {

        if (!processingControlService.checkIfProcessed(ProcessingControlService.TablesList.DEBTS.getTableName())) {
            slackService.pushMessage(format(SERVICE_NAME + " processing canceled - debts table was not processed! [%s]", TIMESTAMP()));
            return;
        }

        slackService.pushMessage(format(SERVICE_NAME + " processing started [%s]", TIMESTAMP()));

        List<History> historyDataPart1 = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.historyScript.split(" ")).replace("/", "-"), History[].class));
        List<History> historyDataPart2 = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.historyScript.split(" ")).replace("/", "-"), History[].class));
        List<History> historyDataPart3 = Arrays.asList(mapper.readValue(executor.executeCommand(scripts.historyScript.split(" ")).replace("/", "-"), History[].class));
        List<History> historyData = of(historyDataPart1, historyDataPart2, historyDataPart3).flatMap(Collection::stream).collect(Collectors.toList());

        for (int n = 1 ; n <= 2500; n++ ) {
            historyData.get(n).setHistoryId(String.valueOf(n));
            historyData.get(n).setVisitorId(String.valueOf(n));
            historyData.get(n).setBookNumber(String.valueOf(n*5+1));

            historyData.get(n).setBookExpectedReturnDate(genExpectedReturnDate(historyData.get(n).getBookPickDate()));
            historyData.get(n).setBookActualReturnDate(genActualReturnDate(n, historyData.get(n).getBookExpectedReturnDate()));

            historyData.get(n).setIsFailedBookReturn(n <= 1500);
            historyData.get(n).setDebt(debtRetrieve(n));
            historyData.get(n).setFeedback(new Random().nextInt(5)+1);
            historyData.get(n).setComment("");
        }
        historyRepository.saveAll(historyData.subList(1, 2501));
        processingControlService.markAsCompleted(ProcessingControlService.TablesList.HISTOTY);
        slackService.pushMessage(format(SERVICE_NAME + " processing finished [%s]", TIMESTAMP()));
    }

    private Debt debtRetrieve(int debtId) {
        if (debtId <= 1500) {
            return debtRepository.findByDebtId(String.valueOf(debtId))
                    .orElseThrow(() -> new DebtNotFoundException(format("Debt with Id [%s] was not found.", debtId)));
        }

        return null;
    }

    private Date genActualReturnDate(int recordNo, Date expectedReturnDate) {
        return recordNo <= 1500 ? DateUtils.addDays(expectedReturnDate, new Random().nextInt(20 - 1) + 1 + 1)
                                : DateUtils.addDays(expectedReturnDate, new Random().nextInt(5 - 3) + 1 + 1);
    }

    private Date genExpectedReturnDate(Date pickDate) {
        return DateUtils.addDays(pickDate, new Random().nextInt(20 - 3) + 1 + 3);
    }
}
