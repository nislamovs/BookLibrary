package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "history")
public class History extends AbstractDocument {

    private String historyId;
    private String visitorId;
    private String bookNumber;

    private Date bookPickDate;

    private Date bookExpectedReturnDate;

    private Date bookActualReturnDate;

    private Boolean isFailedBookReturn;

    @OneToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval = true)
    @JoinProperty(name="debt")
    private Debt debt;

    private int feedback;

    private String comment;

}
