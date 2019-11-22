package com.booklibrary.app.services;

import com.booklibrary.app.services.interfaces.IReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {

//    private final BookRepository bookRepository;
//
//    @Override
//    public Book getBookById(String bookId) {
//        return bookRepository.findByBookId(bookId)
//            .orElseThrow(() -> new BookNotFoundException(format("Book with Id [%s] was not found.", bookId)));
//    }
//
//    @Override
//    public Book getBookByNumber(String bookNumber) {
//        return bookRepository.findOneByBookNumber(bookNumber)
//            .orElseThrow(() -> new BookNotFoundException(format("Book with number [%s] was not found.", bookNumber)));
//    }
}
