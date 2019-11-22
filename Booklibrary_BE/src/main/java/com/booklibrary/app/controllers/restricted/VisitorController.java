package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IVisitorController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.VisitorRequest;
import com.booklibrary.app.services.VisitorService;
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
public class VisitorController implements IVisitorController {

    private final VisitorService visitorService;

    @GetMapping("/visitor/all")
    public ResponseEntity<?> getAllVisitors() {
        log.info("Retrieving visitor list.");
        return ok(visitorService.getAllVisitors().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/visitor", params = { "size", "page" })
    public ResponseEntity<?> getAllVisitors( @DefaultValue("1") @RequestParam(value="page") int page,
                                             @DefaultValue("10") @RequestParam(value="size") int size) {

        log.info("Retrieving page of visitor list. Page[{}], size[{}]", page, size);
        return ok(visitorService.getVisitors(page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/visitor")
    public ResponseEntity<?> addVisitor(@Valid @RequestBody VisitorRequest visitorRequest) {
        log.info("Saving new visitor.");
        return ok(from(visitorService.addVisitor(visitorRequest)));
    }

    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<?> getVisitorById(@PathVariable("penaltyId") @NotBlank String visitorId) {
        log.info("Retrieving penalty plan by Id: {}", visitorId);
        return ok(toResponse(visitorService.getVisitorById(visitorId)));
    }

    @PutMapping("/visitor")
    public ResponseEntity<?> updateVisitorInfo(VisitorRequest visitorRequest) {
        log.info("Updating visitor info by id {}.", visitorRequest.getVisitorId());
        return ok(from(visitorService.updateVisitor(visitorRequest)));
    }

    @DeleteMapping("/visitor/{visitorId}")
    public ResponseEntity<?> deleteVisitor(@PathVariable("visitorId") @NotBlank String visitorId) {
        log.info("Deleting visitor by Id : {}.", visitorId);
        visitorService.deleteVisitorById(visitorId);
        return (ResponseEntity<?>) ok();
    }
}
