package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.DebtNotFoundException;
import com.booklibrary.app.domain.requests.DebtRequest;
import com.booklibrary.app.models.nosql.Debt;
import com.booklibrary.app.repository.nosql.DebtRepository;
import com.booklibrary.app.services.interfaces.IDebtService;
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
public class DebtService implements IDebtService {

    private final DebtRepository debtRepository;

    @Override
    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    @Override
    public Page<Debt> getDebts(int page, int size) {
        return debtRepository.findAll(of(page, size));
    }

    @Override
    public Debt getDebtById(String debtId) {
        return debtRepository.findDebtByDebtId(debtId)
            .orElseThrow(() -> new DebtNotFoundException(format("Debt with Id [%s] was not found.", debtId)));
    }

    @Override
    public Debt registerDebt(DebtRequest debtRequest) {
        return debtRepository.save(toDocument(debtRequest));
    }

    @Override
    public Debt updateDebt(DebtRequest debtRequest) {
        return debtRepository.save(toDocument(debtRequest));
    }

    @Override
    public void deleteDebtById(String debtId) {
        debtRepository.deleteDebtByDebtId(debtId);
    }
}
