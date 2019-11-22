package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.Debt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebtRepository extends MongoRepository<Debt, String> {


    Optional<Debt> findDebtByDebtId(String debtId);
    Optional<Debt> findDebtByBookNumber(String number);
    void deleteDebtByDebtId(String debtId);

}
