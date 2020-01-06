package com.dbpopulator.app.services;


import com.dbpopulator.app.converters.beanConverters.ProcessingStatusConverter;
import com.dbpopulator.app.domain.exceptions.ServiceNotFoundException;
import com.dbpopulator.app.domain.exceptions.TableAlreadyProcessedException;
import com.dbpopulator.app.domain.exceptions.TableNotFoundException;
import com.dbpopulator.app.domain.exceptions.TableNotPreprocessedException;
import com.dbpopulator.app.domain.responses.ServiceStatusResponse;
import com.dbpopulator.app.models.ProcessingStatus;
import com.dbpopulator.app.repository.nosql.ProcessingControlRepository;
import com.dbpopulator.app.services.messaging.SlackService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessingControlService {

    private final ProcessingControlRepository processingControlRepository;
    private final SlackService slackService;

    public void validate(@Valid @NotBlank String serviceToTrigger) {
        if (!TablesList.contains(serviceToTrigger))
            throw new ServiceNotFoundException(format("No such service [%s]!", serviceToTrigger));

//        if (!this.checkIfPreprocessed(serviceToTrigger))
//            throw new TableNotPreprocessedException(format("Table [%s] was not preprocessed.", serviceToTrigger));

        if (this.checkIfProcessed(serviceToTrigger))
            throw new TableAlreadyProcessedException(format("Table [%s] is already processed.", serviceToTrigger));
    }

    public Instant markAsCompleted(TablesList tableName) {
        ProcessingStatus status = processingControlRepository.findByTableNameIgnoreCase(tableName.getTableName())
                .orElseThrow(() -> new TableNotFoundException(format("Table with name [%s] was not found in table list.", tableName.getTableName())));
        status.setIsProcessed(true);
        return processingControlRepository.save(status).getLastModifiedDate();
    }

    public Instant markAsPreprocessed(TablesList tableName) {
        ProcessingStatus status = processingControlRepository.findByTableNameIgnoreCase(tableName.getTableName())
                .orElseThrow(() -> new TableNotFoundException(format("Table with name [%s] was not found in table list.", tableName.getTableName())));
        status.setIsPreprocessed(true);
        return processingControlRepository.save(status).getLastModifiedDate();
    }

    public List<ServiceStatusResponse> retrieveTotalStatusInfo() {
        List<ProcessingStatus> processingStatuses = processingControlRepository.findAll();
        return ProcessingStatusConverter.toResponse(processingStatuses);
    }

    public Boolean checkIfProcessed(String tableName) {
        return processingControlRepository.findByTableNameIgnoreCase(tableName)
            .orElseThrow(() -> new TableNotFoundException(format("Table [%s] not found.", tableName)))
                .getIsProcessed();
    }

    public Boolean checkIfPreprocessed(String tableName) {
        return processingControlRepository.findByTableNameIgnoreCase(tableName)
                .orElseThrow(() -> new TableNotFoundException(format("Table [%s] not found.", tableName)))
                .getIsPreprocessed();
    }

    @AllArgsConstructor
    public static enum TablesList {
        BOOKDATA("bookData"),
        BOOKSTORAGE("bookStorage"),
        BOOKPHOTO("bookPhoto"),
        DEBTS("debts"),
        PAYMENTS("payments"),
        HISTOTY("history"),
        PENALTYPLANS("penaltyPlans"),
        VISITORS("visitors");

        @Getter
        String tableName;

        public static boolean contains(String value) {
            return Arrays.stream(TablesList.values())
                        .map(enumVal -> StringUtils.toLowerEnglish(enumVal.getTableName()))
                        .collect(Collectors.toList())
                        .contains(StringUtils.toLowerEnglish(value));
        }

        public static String asString() {
            String result = "";
            result += "[ ";
            for (TablesList enumVal : TablesList.values()) {
                result += " " + enumVal.getTableName();
            }
            result += " ]";

            return result;
        }
    }
}
