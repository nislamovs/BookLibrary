package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.VisitorRequest;
import com.booklibrary.app.models.nosql.VisitorData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVisitorService {

    List<VisitorData> getAllVisitors();

    Page<VisitorData> getVisitors(int page, int size);

    VisitorData getVisitorById(String visitorId);

    VisitorData addVisitor(VisitorRequest visitorRequest);

    VisitorData updateVisitor(VisitorRequest visitorRequest);

    void deleteVisitorById(String visitorId);
}
