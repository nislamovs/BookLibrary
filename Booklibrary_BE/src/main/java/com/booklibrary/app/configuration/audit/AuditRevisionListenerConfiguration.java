package com.booklibrary.app.configuration.audit;

import com.booklibrary.app.models.sql.audit.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditRevisionListenerConfiguration implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
