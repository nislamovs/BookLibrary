package com.dbpopulator.app.controller;

import com.dbpopulator.app.clients.GoogleBookApiClient;
import com.dbpopulator.app.clients.GoogleBookImageRetrieverClient;
import com.dbpopulator.app.domain.exceptions.BookNotFoundException;
import com.dbpopulator.app.models.BookData;
import com.dbpopulator.app.models.BookPhoto;
import com.dbpopulator.app.models.embedded.Address;
import com.dbpopulator.app.repository.nosql.BookDataRepository;
import com.dbpopulator.app.repository.nosql.DebtRepository;
import com.dbpopulator.app.repository.nosql.HistoryRepository;
import com.dbpopulator.app.repository.nosql.VisitorDataRepository;
import com.dbpopulator.app.services.BookDataService;
import com.dbpopulator.app.services.PaymentService;
import com.dbpopulator.app.services.ProcessingControlService;
import com.dbpopulator.app.services.messaging.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.javamoney.moneta.Money;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Contests {

    private final GoogleBookApiClient bookApiClient;
    private final GoogleBookImageRetrieverClient bookPhotoRetrievalClient;
    private final SlackService slackService;
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage alertMessageTemplate;

    private final BookDataRepository bookDataRepository;
    private final HistoryRepository historyRepository;
    private final VisitorDataRepository visitorDataRepository;
    private final DebtRepository debtRepository;
    private final BookDataService bookDataService;
    private final PaymentService paymentService;
    private final ProcessingControlService processingControlService;


    @GetMapping("/test")
    public ResponseEntity<?> test() throws IOException {
        bookDataService.processBookData();
        return noContent().build();
    }

    @GetMapping("/mailtest")
    public ResponseEntity<?> mail() {

        alertMessageTemplate.setText("adasd234234");
        javaMailSender.send(alertMessageTemplate);

        return noContent().build();
    }

    @GetMapping("/getvisitors")
    public ResponseEntity<?> getvis() {
        return ok(visitorDataRepository.findByVisitorId("1").get());
    }

    @GetMapping("/gethistory")
    public ResponseEntity<?> gethistory() {
        return ok(historyRepository.findByHistoryId("1").get());
    }

    @GetMapping("/getdebt")
    public ResponseEntity<?> getdebt() {
        return ok(debtRepository.findByDebtId("1").get());
    }

    @GetMapping("/getbook")
    public ResponseEntity<?> getbook() {
        return ok(bookDataRepository.findByBookId("5").get());
    }

    @GetMapping("/btest")
    public ResponseEntity<?> btest() {

        BookData b= bookDataRepository.findByBookId("5").get();


        return ok(bookDataService.getDataFromGoogleAndFill(b));
    }


    @GetMapping("/slacktest")
    public ResponseEntity<?> slack() {

        Address addr = Address.builder().city("Riga").postalCode("lv1010")
                .street("asdasdas streeet").additionalInfo("no info bro!").build();

//        slackService.pushMessage(addr);
//        slackService.pushMessage("2345g235g23542345g124124121212312ff113f2123f");
        slackService.pushMessage(format("History records processing finished [%s]", TIMESTAMP()));

        return noContent().build();
    }

    @GetMapping("/feign")
    public ResponseEntity<?> feign() {
        bookDataService.populateBookData();
        return ok(now());
    }

    @GetMapping("/feign12222")
    @SneakyThrows
    public ResponseEntity<?> feign121212() {
//
//        here retrieve page of 2000 books(isbn's) with empty fields
        BookData bookData = bookDataRepository.findByBookId("5")
                .orElseThrow(() -> new BookNotFoundException("Oblom!"));
//

// here provide records processing in for loop

        JSONObject googleResponse = new JSONObject(bookApiClient.getBookData("isbn:" + bookData.getIsbn()))
                .getJSONArray("items").getJSONObject(0);

        //bookId is ok
        bookData.setBookTitle(googleResponse.getJSONObject("volumeInfo").getString("title"));
        //bookIsbn is ok
        bookData.setPublisher(googleResponse.getJSONObject("volumeInfo").getString("publisher"));
        List<String> authors = new ArrayList<>();
        googleResponse.getJSONObject("volumeInfo").getJSONArray("authors").forEach(author -> authors.add((String)author));
        bookData.setAuthors(authors);
        //book categories are ok
        bookData.setSearchInfo(googleResponse.getJSONObject("searchInfo").getString("textSnippet"));
        bookData.setAnnotation(googleResponse.getJSONObject("volumeInfo").getString("description"));
        bookData.setPages(googleResponse.getJSONObject("volumeInfo").getInt("pageCount"));

        Date date = new SimpleDateFormat("yyyy").parse(String.valueOf(LocalDate.now()));
//        bookData.setPublishedDate(DateUtils.setYears(date, Integer.parseInt(googleResponse.getJSONObject("volumeInfo").getString("publishedDate"))));
        bookData.setLanguage(BookData.Language.valueOf(StringUtils.upperCase(googleResponse.getJSONObject("volumeInfo").getString("language"))));
        bookData.setTotalCount(new Random().nextInt(24) + 5);
        bookData.setAvailableCount(bookData.getTotalCount());
        bookData.setPopularityRate(BigDecimal.valueOf(new Random().nextDouble() * (5D - 1D)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        bookData.setInternalPrice(Money.of(BigDecimal.valueOf(new Random().nextDouble() * (79.99D - 5.00D)).setScale(2, BigDecimal.ROUND_HALF_DOWN), "EUR"));
        bookData.setBookPhoto(
                BookPhoto.builder().bookPhotoId(bookData.getBookId())
                        .isbn(bookData.getIsbn())
                        .bookPhoto(retrieveBookPhoto(googleResponse.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail")))
                        .build()
        );

        return ok(bookData);
    }

    @SneakyThrows
    private String retrieveBookPhoto(String link) {
        //returns Base64 encoded image
        MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(link).build().getQueryParams();
        Files.write(Paths.get("imga.png"), bookPhotoRetrievalClient.getBookPhoto(parameters.get("id").get(0)));

        return Base64.getEncoder().encodeToString(bookPhotoRetrievalClient.getBookPhoto(parameters.get("id").get(0)));
    }

    public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
                .collect(Collectors.toList());
    }
}
