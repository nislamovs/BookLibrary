package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findAllByDebtId(String debtId);

    List<Payment> findAllByCardholder(String cardholder);

    List<Payment> findAllByVisitorId(String visitorId);

}
