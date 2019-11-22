package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.DebtRequest;
import org.springframework.http.ResponseEntity;

public interface IDebtController {

    ResponseEntity<?> getAllDebts();

    ResponseEntity<?> addDebt(DebtRequest debt);

    ResponseEntity<?> getDebtById(String debtId);

    ResponseEntity<?> updateDebtStatus(DebtRequest debt);

    ResponseEntity<?> deleteDebt(String debtId);

}
