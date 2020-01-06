package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

        @Query("{ 'paymentId' : ?0 }")
        Optional<Payment> findByPaymentId(String paymentId);
}
