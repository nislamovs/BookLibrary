package com.dbpopulator.app.models.audit;


import lombok.Getter;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class AbstractDocument {

    @Id
    private String id;

    @Field(name="createdDate")
    @CreatedDate
    private Instant createdDate;

    @Field(name="createdBy")
    @CreatedBy
    private String createdBy;

    @Field(name="lastModifiedDate")
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Field(name="lastModifiedBy")
    @LastModifiedBy
    private String lastModifiedBy;

    @Field(name="version")
//    @Version
    private Long version;
}
