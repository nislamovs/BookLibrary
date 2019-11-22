package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface IPaymentController {

    ResponseEntity<?> getAllPayments();

    ResponseEntity<?> getAllPayments(int page, int size);

    ResponseEntity<?> getAllPaymentsByCardholder(String cardholder);

    ResponseEntity<?> getAllPaymentsByVisitorId(String visitorId);

    ResponseEntity<?> addPayment(PaymentRequest payment);

    ResponseEntity<?> getPaymentById(String paymentId);

    ResponseEntity<?> updatePaymentInfo(PaymentRequest payment);

    ResponseEntity<?> deletePayment(String paymentId);
}
