package com.booklibrary.app.models.nosql.audit;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractDocument {

    @Id
    private String id;

//    @CreatedDate
//    private Instant createdDate;
//
//    @CreatedBy
//    private String createdBy;
//
//    @LastModifiedDate
//    private Instant lastModifiedDate;
//
//    @LastModifiedBy
//    private String lastModifiedBy;

    @Version
    private Long version;
}
