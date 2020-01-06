package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.Debt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebtRepository extends MongoRepository<Debt, String> {

    @Query("{ 'debtId' : ?0 }")
    Optional<Debt> findByDebtId(String debtId);

}
