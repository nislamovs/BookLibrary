package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.HistoryRecordNotFoundException;
import com.booklibrary.app.domain.requests.HistoryRequest;
import com.booklibrary.app.models.nosql.History;
import com.booklibrary.app.repository.nosql.HistoryRepository;
import com.booklibrary.app.services.interfaces.IHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.booklibrary.app.converters.orikaConverters.ToDocumentConverters.toDocument;
import static java.lang.String.format;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService implements IHistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public List<History> getAllHistoryRecords() {
        return historyRepository.findAll();
    }

    @Override
    public Page<History> getHistoryRecords(int page, int size) {
        return historyRepository.findAll(of(page, size));
    }

    @Override
    public History getHistoryById(String historyId) {
        return historyRepository.findByHistoryId(historyId)
            .orElseThrow(() -> new HistoryRecordNotFoundException(format("History record with Id [%s] does not exist.", historyId)));
    }

    @Override
    public History addHistoryRecord(HistoryRequest historyRequest) {
        return historyRepository.save(toDocument(historyRequest));
    }

    @Override
    public List<History> getHistoryRecordsByVisitorId(String visitorId) {
        return historyRepository.findByVisitorId(visitorId);
//            .orElseThrow(() -> new VisitorNotFoundException(format("Visitor with Id [%s] does not exist.", visitorId)));
    }

    @Override
    public List<History> getHistoryRecordsByBooknumber(String booknumber) {
        return historyRepository.findByBookNumber(booknumber);
//            .orElseThrow(() -> new BookNotFoundException(format("Book with number [%s] does not exist.", booknumber)));
    }

    @Override
    public List<History> getHistoryRecordsByBookId(String bookId) {
        return historyRepository.findByBookId(bookId);
//            .orElseThrow(() -> new BookNotFoundException(format("Book with Id [%s] does not exist.", bookId)));
    }

    @Override
    public History updateHistoryRecord(HistoryRequest historyRequest) {
        return historyRepository.save(toDocument(historyRequest));
    }

    @Override
    public void deleteHistoryById(String historyId) {
        historyRepository.deleteByHistoryId(historyId);
    }

}
