package com.booklibrary.app.domain.responses.ackResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryRecordCreatedResponse {

    private String visitorId;

    private String historyId;

    private String bookNumber;

    private Instant createdDate;

    private String createdBy;

}
