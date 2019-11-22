package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.PaymentNotFoundException;
import com.booklibrary.app.domain.requests.PaymentRequest;
import com.booklibrary.app.models.nosql.Payment;
import com.booklibrary.app.repository.nosql.PaymentRepository;
import com.booklibrary.app.services.interfaces.IPaymentService;
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
public class PaymentService  implements IPaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Page<Payment> getPayments(int page, int size) {
        return paymentRepository.findAll(of(page, size));
    }

    @Override
    public List<Payment> getPaymentsByCardholder(String cardholder) {
        return paymentRepository.findAllByCardholder(cardholder);
//            TODO add check of existance of rquested param in db
//            .orElseThrow(() -> new CardholderNotFoundException(format("Cardholder [%s] does not exist.", cardholder)));
    }

    @Override
    public List<Payment> getPaymentsByVisitorId(String visitorId) {
        return paymentRepository.findAllByVisitorId(visitorId);
        //            TODO add check of existance of rquested param in db
//            .orElseThrow(() -> new VisitorNotFoundException(format("Visitor with Id [%s] does not exist.", visitorId)));
    }

    @Override
    public Payment getPaymentById(String paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException(format("Payment with Id [%s] was not found.", paymentId)));
    }

    @Override
    public Payment addPayment(PaymentRequest paymentRequest) {
//        TODO convert DTO to entity
        return paymentRepository.save(toDocument(paymentRequest));
    }

    @Override
    public Payment updatePayment(PaymentRequest paymentRequest) {
        //        TODO convert DTO to entity
        return paymentRepository.save(toDocument(paymentRequest));
    }

    @Override
    public void deletePaymentById(String paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
