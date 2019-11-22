package com.booklibrary.app.models.sql.audit;

import com.booklibrary.app.configuration.audit.AuditRevisionListenerConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@RevisionEntity(AuditRevisionListenerConfiguration.class)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "REVINFO", schema = "audit")
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "REVTSTMP")),
        @AttributeOverride(name = "id",        column = @Column(name = "REV"))
})
@Getter
@Setter
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "REVISION_DATE", nullable = false, updatable = false)
    private Date createdDate;
}

