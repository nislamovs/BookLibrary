package com.booklibrary.app.converters.orikaConverters;

import com.booklibrary.app.configuration.OrikaConfiguration;
import com.booklibrary.app.domain.requests.*;
import com.booklibrary.app.models.nosql.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Slf4j
@RequiredArgsConstructor
public class ToDocumentConverters {

    private static final MapperFacade mapper = new OrikaConfiguration();

    public static BookData toDocument(BookDataRequest bookDataRequest) {
        log.info(">>>>>>>>>>>>>..        "+bookDataRequest);
        return mapper.map(bookDataRequest, BookData.class);
    }

    public static Book toDocument(BookStorageRequest bookRequest) {
        log.info(">>>>>>>>>>>>>..        "+bookRequest);
        return mapper.map(bookRequest, Book.class);
    }

    public static Debt toDocument(DebtRequest debtRequest) {
        log.info(">>>>>>>>>>>>>..        "+debtRequest);
        return mapper.map(debtRequest, Debt.class);
    }

    public static Payment toDocument(PaymentRequest paymentRequest) {
        log.info(">>>>>>>>>>>>>..        "+paymentRequest);
        return mapper.map(paymentRequest, Payment.class);
    }

    public static History toDocument(HistoryRequest historyRequest) {
        log.info(">>>>>>>>>>>>>..        "+historyRequest);
        return mapper.map(historyRequest, History.class);
    }

    public static PenaltyPlan toDocument(PenaltyPlanRequest penaltyPlanRequest) {
        log.info(">>>>>>>>>>>>>..        "+penaltyPlanRequest);
        return mapper.map(penaltyPlanRequest, PenaltyPlan.class);
    }

    public static VisitorData toDocument(VisitorRequest visitorDataRequest) {
        log.info(">>>>>>>>>>>>>..        "+visitorDataRequest);
        return mapper.map(visitorDataRequest, VisitorData.class);
    }
}
