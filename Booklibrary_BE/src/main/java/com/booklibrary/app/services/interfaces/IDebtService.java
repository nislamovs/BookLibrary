package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.DebtRequest;
import com.booklibrary.app.models.nosql.Debt;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDebtService {

    List<Debt> getAllDebts();

    Page<Debt> getDebts(int page, int size);

    Debt getDebtById(String debtId);

    Debt registerDebt(DebtRequest debtRequest);

    Debt updateDebt(DebtRequest debtRequest);

    void deleteDebtById(String debtId);
}
