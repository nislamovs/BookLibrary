package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IDebtController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.DebtRequest;
import com.booklibrary.app.services.DebtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.stream.Collectors;

import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.toResponse;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DebtController implements IDebtController {

    private final DebtService debtService;

    @GetMapping("/debt/all")
    public ResponseEntity<?> getAllDebts() {
        log.info("Retrieving all debts .");
        return ok(debtService.getAllDebts().stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping(path = "/debt", params = { "size", "page" })
    public ResponseEntity<?> getAllDebts( @DefaultValue("1") @RequestParam(value="page") int page,
                                          @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of debt list. Page[{}], size[{}]", page, size);
        return ok(debtService.getDebts(page, size).getContent().stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @PostMapping("/debt")
    public ResponseEntity<?> addDebt(@Valid @RequestBody DebtRequest debtRequest) {
        log.info("Registering new debt.");
        return ok(toResponse(debtService.registerDebt(debtRequest)));
    }

    @GetMapping("/debt/{debtId}")
    public ResponseEntity<?> getDebtById(@PathVariable("debtId") @NotBlank String debtId) {
        log.info("Retrieving debt by Id: {}", debtId);
        return ok(toResponse(debtService.getDebtById(debtId)));
    }

    @PutMapping("/debt")
    public ResponseEntity<?> updateDebtStatus(DebtRequest debtRequest) {
        log.info("Updating debt info by id {}.", debtRequest.getDebtId());
        return ok(toResponse(debtService.updateDebt(debtRequest)));
    }

    @DeleteMapping("/debt/{debtId}")
    public ResponseEntity<?> deleteDebt(@PathVariable("debtId") @NotBlank String debtId) {
        log.info("Deleting debt by Id : {}.", debtId);
        debtService.deleteDebtById(debtId);
        return (ResponseEntity<?>) ok();
    }
}
