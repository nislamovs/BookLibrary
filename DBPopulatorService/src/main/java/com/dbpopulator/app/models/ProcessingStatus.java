package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "processing_status")
public class ProcessingStatus extends AbstractDocument {

    @Field(name="processId")
    String processId;

    @Field(name="tableName")
    String tableName;

    @Field(name="isProcessed")
    Boolean isProcessed;

    @Field(name="isPreprocessed")
    Boolean isPreprocessed;

}
