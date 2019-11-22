package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.HistoryRequest;
import org.springframework.http.ResponseEntity;

public interface IHistoryController {

    ResponseEntity<?> getAllHistoryRecords();

    ResponseEntity<?> getAllHistoryRecords(int page, int size);

    ResponseEntity<?> addHistoryRecord( HistoryRequest history);

    ResponseEntity<?> getHistoryById(String historyId);

    ResponseEntity<?> getHistoryByVisitorId(String visitorId);

    ResponseEntity<?> getHistoryByBookNumber(String booknumber);

    ResponseEntity<?> getHistoryByBookId(String bookId);

    ResponseEntity<?> updateHistoryRecord(HistoryRequest history);

    ResponseEntity<?> deleteHistoryRecord(String historyId);

}
