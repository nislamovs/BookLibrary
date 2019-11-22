package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IPaymentController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.PaymentRequest;
import com.booklibrary.app.services.PaymentService;
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
public class PaymentController implements IPaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/all")
    public ResponseEntity<?> getAllPayments() {
        log.info("Retrieving all payments .");
        return ok(paymentService.getAllPayments().stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping(path = "/payment", params = { "size", "page" })
    public ResponseEntity<?> getAllPayments( @DefaultValue("1") @RequestParam(value="page") int page,
                                             @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of payment list. Page[{}], size[{}]", page, size);
        return ok(paymentService.getPayments(page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getAllPaymentsByCardholder(@RequestParam(value="cardHolder") String cardholder) {
        log.info("Retrieving all payments by card holder name {}.", cardholder);
        return ok(paymentService.getPaymentsByCardholder(cardholder).stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getAllPaymentsByVisitorId(@RequestParam(value="visitorId") String visitorId) {
        log.info("Retrieving payment list by visitorId {}.", visitorId);
        return ok(paymentService.getPaymentsByVisitorId(visitorId).stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/payment")
    public ResponseEntity<?> addPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Saving new payment.");
        return ok(from(paymentService.addPayment(paymentRequest)));
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable("paymentId") @NotBlank String paymentId) {
        log.info("Retrieving penalty plan by Id: {}", paymentId);
        return ok(toResponse(paymentService.getPaymentById(paymentId)));
    }

    @PutMapping("/payment")
    public ResponseEntity<?> updatePaymentInfo(PaymentRequest paymentRequest) {
        log.info("Updating payment info by id {}.", paymentRequest.getPaymentId());
        return ok(from(paymentService.updatePayment(paymentRequest)));
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable("paymentId") @NotBlank String paymentId) {
        log.info("Deleting payment by Id : {}.", paymentId);
        paymentService.deletePaymentById(paymentId);
        return (ResponseEntity<?>) ok();
    }
}
