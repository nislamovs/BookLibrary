package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IHistoryController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.HistoryRequest;
import com.booklibrary.app.services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.stream.Collectors;

import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.from;
import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.toResponse;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HistoryController implements IHistoryController {

    private final HistoryService historyService;

    @GetMapping("/history/all")
    public ResponseEntity<?> getAllHistoryRecords() {
        log.info("Retrieving all history records .");
        return ok(historyService.getAllHistoryRecords().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/history", params = { "size", "page" })
    public ResponseEntity<?> getAllHistoryRecords( @DefaultValue("1") @RequestParam(value="page") int page,
                                                   @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of history record list. Page[{}], size[{}]", page, size);
        return ok(historyService.getHistoryRecords(page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/history")
    public ResponseEntity<?> addHistoryRecord(@Valid @RequestBody HistoryRequest historyRequest) {
        log.info("Registering new history record.");
        return ok(from(historyService.addHistoryRecord(historyRequest)));
    }

    @GetMapping("/history/{historyId}")
    public ResponseEntity<?> getHistoryById(@PathVariable("historyId") @NotBlank String historyId) {
        log.info("Retrieving history record by Id: {}", historyId);
        return ok(toResponse(historyService.getHistoryById(historyId)));
    }

    @GetMapping("/history/{visitorId}")
    public ResponseEntity<?> getHistoryByVisitorId(@PathVariable("visitorId") @NotBlank String visitorId) {
        log.info("Retrieving history record by visitor Id: {}", visitorId);
        return ok(historyService.getHistoryRecordsByVisitorId(visitorId).stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/history/{booknumber}")
    public ResponseEntity<?> getHistoryByBookNumber(@PathVariable("booknumber") @NotBlank String booknumber) {
        log.info("Retrieving history record by book number: {}", booknumber);
        return ok(historyService.getHistoryRecordsByBooknumber(booknumber).stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/history/{bookId}")
    public ResponseEntity<?> getHistoryByBookId(@PathVariable("bookId") @NotBlank String bookId) {
        log.info("Retrieving history record by book Id: {}", bookId);
        return ok(historyService.getHistoryRecordsByBookId(bookId).stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PutMapping("/history")
    public ResponseEntity<?> updateHistoryRecord(HistoryRequest historyRequest) {
        log.info("Updating history record info by id {}.", historyRequest.getHistoryId());
        return ok(toResponse(historyService.updateHistoryRecord(historyRequest)));
    }

    @DeleteMapping("/history/{historyId}")
    public ResponseEntity<?> deleteHistoryRecord(@PathVariable("historyId") @NotBlank String historyId) {
        log.info("Deleting history record by Id : {}.", historyId);
        historyService.deleteHistoryById(historyId);
        return (ResponseEntity<?>) ok();
    }
}
