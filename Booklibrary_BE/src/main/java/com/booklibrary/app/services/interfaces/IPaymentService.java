package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.PaymentRequest;
import com.booklibrary.app.models.nosql.Payment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPaymentService {

    List<Payment> getAllPayments();

    Page<Payment> getPayments(int page, int size);

    List<Payment> getPaymentsByCardholder(String cardholder);

    List<Payment> getPaymentsByVisitorId(String visitorId);

    Payment getPaymentById(String paymentId);

    Payment addPayment(PaymentRequest paymentRequest);

    Payment updatePayment(PaymentRequest paymentRequest);

    void deletePaymentById(String paymentId);
}
