package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.HistoryRequest;
import com.booklibrary.app.models.nosql.History;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IHistoryService {

    List<History> getAllHistoryRecords();

    Page<History> getHistoryRecords(int page, int size);

    History getHistoryById(String historyId);

    History addHistoryRecord(HistoryRequest historyRequest);

    List<History> getHistoryRecordsByVisitorId(String visitorId);

    List<History> getHistoryRecordsByBooknumber(String booknumber);

    List<History> getHistoryRecordsByBookId(String bookId);

    History updateHistoryRecord(HistoryRequest historyRequest);

    void deleteHistoryById(String paymentId);
}
