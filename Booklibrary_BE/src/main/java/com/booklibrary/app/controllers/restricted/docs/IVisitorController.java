package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.VisitorRequest;
import org.springframework.http.ResponseEntity;

public interface IVisitorController {

    ResponseEntity<?> addVisitor(VisitorRequest visitorRequest);

    ResponseEntity<?> getAllVisitors();

    ResponseEntity<?> getAllVisitors( int page, int size);

    ResponseEntity<?> getVisitorById(String visitorId);

    ResponseEntity<?> updateVisitorInfo(VisitorRequest visitorRequest);

    ResponseEntity<?> deleteVisitor(String visitorId);

}
