package com.dbpopulator.app.services;


import com.dbpopulator.app.models.Book;
import com.dbpopulator.app.models.BookData;
import com.dbpopulator.app.repository.nosql.BookStorageRepository;
import com.dbpopulator.app.services.messaging.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookStorageService {

    private static final String SERVICE_NAME = "BookStorageService";
    private final SlackService slackService;
    private final BookStorageRepository bookStorageRepository;

    public List<Book> processBookStorage(BookData bookData) {
        List<Book> bookStorage = new ArrayList<>();
        int currentCount = (int) bookStorageRepository.count();
        int bookTotalCount = bookData.getTotalCount();

        for (int n = currentCount + 1; n <= currentCount + bookTotalCount; n++) {
            bookStorage.add(Book.builder()
                    .bookId(bookData.getBookId())
                    .bookNumber(String.valueOf(n))
                    .isbn(bookData.getIsbn())
                    .visitorId(n <= 2500 ? String.valueOf(n) : null)
                    .status(provideBookStatus(bookData))
                    .build());
        }

        return bookStorageRepository.saveAll(bookStorage);
    }

    private Book.Status provideBookStatus(BookData bookData) {
        return Integer.parseInt(bookData.getBookId()) <= 1500
                ? Book.Status.LOST
                : Book.Status.ON_SHELF;
    }
}
