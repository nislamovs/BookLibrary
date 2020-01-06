package com.dbpopulator.app.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceStatusResponse {

    String processId;

    String tableName;

    boolean isProcessed;

    boolean isPreprocessed;

    Instant createdDate;

    Instant lastModifiedDate;
}
