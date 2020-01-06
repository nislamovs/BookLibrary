package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "history")
public class History extends AbstractDocument {

    @Field(name="historyId")
    private String historyId;

    @Field(name="visitorId")
    private String visitorId;

    @Field(name="bookNumber")
    private String bookNumber;

    @Field(name="bookPickDate")
    private Date bookPickDate;

    @Field(name="bookExpectedReturnDate")
    private Date bookExpectedReturnDate;

    @Field(name="bookActualReturnDate")
    private Date bookActualReturnDate;

    @Field(name="isFailedBookReturn")
    private Boolean isFailedBookReturn;

    @Field(name="debt")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinProperty(name="debt")
    private Debt debt;

    @Field(name="feedback")
    private int feedback;

    @Field(name="comment")
    private String comment;

}
