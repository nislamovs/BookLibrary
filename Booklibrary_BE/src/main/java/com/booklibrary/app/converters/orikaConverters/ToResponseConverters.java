package com.booklibrary.app.converters.orikaConverters;

import com.booklibrary.app.configuration.OrikaConfiguration;
import com.booklibrary.app.domain.responses.ackResponses.*;
import com.booklibrary.app.domain.responses.responses.*;
import com.booklibrary.app.models.nosql.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Slf4j
@RequiredArgsConstructor
public class ToResponseConverters {

    private static final MapperFacade mapper = new OrikaConfiguration();

    public static BookDataResponse toResponse(BookData bookData) {
        return mapper.map(bookData, BookDataResponse.class);
    }

    public static BookStorageResponse toResponse(Book book) {
        return mapper.map(book, BookStorageResponse.class);
    }

    public static DebtResponse toResponse(Debt debt) {
        return mapper.map(debt, DebtResponse.class);
    }

    public static PaymentResponse toResponse(Payment payment) {
        return mapper.map(payment, PaymentResponse.class);
    }

    public static HistoryResponse toResponse(History history) {
        return mapper.map(history, HistoryResponse.class);
    }

    public static PenaltyPlanResponse toResponse(PenaltyPlan penaltyPlan) {
        return mapper.map(penaltyPlan, PenaltyPlanResponse.class);
    }

    public static VisitorResponse toResponse(VisitorData visitorData) {
        return mapper.map(visitorData, VisitorResponse.class);
    }

//Acknowledgements

    public static BookCreatedResponse from(BookData book) {
        return mapper.map(book, BookCreatedResponse.class);
    }

    public static BookSampleCreatedResponse from(Book book) {
        return mapper.map(book, BookSampleCreatedResponse.class);
    }

    public static DebtCreatedResponse from(Debt debt) {
        return mapper.map(debt, DebtCreatedResponse.class);
    }

    public static HistoryRecordCreatedResponse from(History history) {
        return mapper.map(history, HistoryRecordCreatedResponse.class);
    }

    public static PaymentCreatedResponse from(Payment payment) {
        return mapper.map(payment, PaymentCreatedResponse.class);
    }

    public static PenaltyPlanCreatedResponse from(PenaltyPlan penaltyPlan) {
        return mapper.map(penaltyPlan, PenaltyPlanCreatedResponse.class);
    }

    public static VisitorCreatedResponse from(VisitorData visitorData) {
        return mapper.map(visitorData, VisitorCreatedResponse.class);
    }
}
