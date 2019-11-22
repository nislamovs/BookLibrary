package com.booklibrary.app.services.stuff;

import com.booklibrary.app.repository.nosql.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoService {

    private final BookStorageRepository bookStorageRepository;
    private final BookDataRepository bookDataRepository;
    private final HistoryRepository historyRepository;
    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;

//    @Transactional
//    public List<Book> findStatus(String status) {
//        return bookStorageRepository.findBookByStatus(status);
//    }
//
//    @Transactional
//    public BookData getData(String id) {
//        return bookDataRepository.findBookDataByBookId(id).get();
//    }
//
//    @Transactional
//    public History getHistory(String id) {
//        return historyRepository.findHistoryByHistoryId(id);
//    }
//
//    @Transactional
//    public History saveHistory(History history) {
//        return historyRepository.save(history);
//    }
//
//    @Transactional
//    public List<Payment> getPayments(String id) {
//        return paymentRepository.findAllByDebtId(id);
//    }
//
//    @Transactional
//    public Debt getDebt(String id) {
//        return debtRepository.findDebtByDebtId(id).get();
//    }
}
