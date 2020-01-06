package com.dbpopulator.app.services;


import com.dbpopulator.app.clients.GoogleBookApiClient;
import com.dbpopulator.app.clients.GoogleBookImageRetrieverClient;
import com.dbpopulator.app.models.BookData;
import com.dbpopulator.app.models.BookPhoto;
import com.dbpopulator.app.repository.nosql.BookDataRepository;
import com.dbpopulator.app.services.components.CurlScripts;
import com.dbpopulator.app.services.messaging.SlackService;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.javamoney.moneta.Money;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.dbpopulator.app.services.messaging.SlackService.TIMESTAMP;
import static java.lang.String.format;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookDataService {

    private static final String SERVICE_NAME = "BookDataService";

    private final GoogleBookImageRetrieverClient bookPhotoRetrievalClient;
    private final GoogleBookApiClient bookApiClient;

    private final CurlScripts scripts;
    private final BookDataRepository bookDataRepository;
    private final SlackService slackService;
    private final ProcessingControlService processingControlService;
    private final BookStorageService bookStorageService;


    private final String PUBLICATION_YEAR_INTERVAL = "1970-2018";
    private final String PRODUCT_TYPE = "books";    //could be journals
    
    private final String MANAGEMENT_CATALOG_NO="MANAGEMENT-27382";
    private final String COMPUTER_SCIENCE_CATALOG_NO="COMPUTER_SCIENCE-27364";
    private final String PSYCHOLOGY_CATALOG_NO="PSYCHOLOGY-27388";
    private final String ECONOMICS_CATALOG_NO="ECONOMICS-27386";
    private final String MEDICINE_CATALOG_NO="MEDICINE-27336";
    private final String ENGINEERING_CATALOG_NO="ENGINEERING-27370";
    private final String BIOLOGY_CATALOG_NO="BIOLOGY-27346";
    private final String PHYSICS_CATALOG_NO="PHYSICS-27378";
    private final String TOXICOLOGY_CATALOG_NO="TOXICOLOGY-27358";
    private final String VETERINARY_CATALOG_NO="VETERINARY-27344";
    private final String MATHEMATICS_CATALOG_NO="MATHEMATICS-27376";
    private final String PHARMACOLOGY_CATALOG_NO="PHARMACOLOGY-27356";
    private final String CHEMISTRY_CATALOG_NO="CHEMISTRY-27362";
    private final String MICROBIOLOGY_CATALOG_NO="MICROBIOLOGY-27352";
    private final String SOCIAL_SCIENCE_CATALOG_NO="SOCIAL_SCIENCE-27390";
    private final String MATERIAL_SCIENCE_CATALOG_NO="MATERIAL_SCIENCE-27374";
    
    private final List<String> CATALOGS_IDS = ImmutableList.of(
            MANAGEMENT_CATALOG_NO, COMPUTER_SCIENCE_CATALOG_NO, PSYCHOLOGY_CATALOG_NO,
            ECONOMICS_CATALOG_NO, MEDICINE_CATALOG_NO, ENGINEERING_CATALOG_NO,
            BIOLOGY_CATALOG_NO, PHYSICS_CATALOG_NO, TOXICOLOGY_CATALOG_NO,
            VETERINARY_CATALOG_NO, MATHEMATICS_CATALOG_NO, PHARMACOLOGY_CATALOG_NO,
            CHEMISTRY_CATALOG_NO, MICROBIOLOGY_CATALOG_NO, SOCIAL_SCIENCE_CATALOG_NO,
            MATERIAL_SCIENCE_CATALOG_NO
    );

    @Transactional
    public void populateBookData() {

        if (!processingControlService.checkIfPreprocessed(ProcessingControlService.TablesList.BOOKDATA.getTableName()))
            preprocessBookData();

        processBookData();
    }

    public void processBookData() {

        //Here retrieve page of 2000 books(isbn's) with empty fields
        List<BookData> books = bookDataRepository.findUnprocessed(of(0, 2000));

        for (BookData bookData : books) {
            System.out.println("#"+bookData.getBookId());
            try {
//                BookData preparedBookData = getDataFromGoogleAndFill(bookData);
//                bookStorageService.processBookStorage(preparedBookData);
//                bookDataRepository.save(preparedBookData);
                bookData = getDataFromGoogleAndFill(bookData);
                bookStorageService.processBookStorage(bookData);
                bookDataRepository.save(bookData);
            } catch (JSONException ex) {
                //NOP
            }
        }
    }

//    @SneakyThrows
    public BookData getDataFromGoogleAndFill(BookData bookData) {
        JSONObject response= new JSONObject(bookApiClient.getBookData("isbn:" + bookData.getIsbn()))
                    .getJSONArray("items").getJSONObject(0);

        bookData.setRawPayload(response.toString());
        //bookId is ok
        bookData.setBookTitle(response.getJSONObject("volumeInfo").getString("title"));
        //bookIsbn is ok
        bookData.setPublisher(response.getJSONObject("volumeInfo").getString("publisher"));
        List<String> authors = new ArrayList<>();
        response.getJSONObject("volumeInfo").getJSONArray("authors").forEach(author -> authors.add((String)author));
        bookData.setAuthors(authors);
        //book categories are ok
        bookData.setSearchInfo(response.getJSONObject("searchInfo").getString("textSnippet"));
        bookData.setAnnotation(response.getJSONObject("volumeInfo").getString("description"));
        bookData.setPages(response.getJSONObject("volumeInfo").getInt("pageCount"));
        bookData.setPublishedDate(response.getJSONObject("volumeInfo").getString("publishedDate"));
        bookData.setLanguage(BookData.Language.valueOf(StringUtils.upperCase(response.getJSONObject("volumeInfo").getString("language"))));
        bookData.setTotalCount(new Random().nextInt(24) + 5);
        bookData.setAvailableCount(bookData.getTotalCount());
        bookData.setPopularityRate(BigDecimal.valueOf(new Random().nextDouble() * (5D - 1D)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        bookData.setInternalPrice(Money.of(BigDecimal.valueOf(new Random().nextDouble() * (79.99D - 5.00D) + 5.00D).setScale(2, BigDecimal.ROUND_HALF_DOWN), "EUR"));
        bookData.setBookPhoto(
                BookPhoto.builder()
                        .bookPhotoId(bookData.getBookId())
                        .isbn(bookData.getIsbn())
                        .bookPhoto(retrieveBookPhoto(response.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail")))
                        .build()
        );

        return bookData;
    }

    private String retrieveBookPhoto(String link) {
        //returns Base64 encoded image
        MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(link).build().getQueryParams();

        return Base64.getEncoder().encodeToString(bookPhotoRetrievalClient.getBookPhoto(parameters.get("id").get(0)));
    }

    public void preprocessBookData() {
        slackService.pushMessage(format(SERVICE_NAME + " preprocessing started [%s]", TIMESTAMP()));
        Map<String, List<String>> allISBNs = new HashMap<>();
        AtomicInteger bookId = new AtomicInteger(0);

        for (String catalog : CATALOGS_IDS) {
            String catalogName = catalog.split("-")[0];
            List<String> isbns = retrieveISBNNumbers(PUBLICATION_YEAR_INTERVAL, PRODUCT_TYPE, catalog);

            allISBNs.put(catalogName, isbns);
//            break;  //to delete
        }

        allISBNs.forEach((category, bookISBNs) -> {
            bookISBNs.forEach(isbn -> {
                BookData bookData = BookData.builder()
                    .bookId(String.valueOf(bookId.incrementAndGet()))
                    .isbn(isbn)
                    .categories(ImmutableList.of(BookData.Category.valueOf(category)))
//                    .bookPhoto(new BookPhoto())
                    .build();

                    bookDataRepository.save(bookData);
                }
            );
        });

        processingControlService.markAsPreprocessed(ProcessingControlService.TablesList.BOOKDATA);
        processingControlService.markAsPreprocessed(ProcessingControlService.TablesList.BOOKSTORAGE);
        processingControlService.markAsPreprocessed(ProcessingControlService.TablesList.BOOKPHOTO);
        slackService.pushMessage(format(SERVICE_NAME + " preprocessing finished [%s]", TIMESTAMP()));
    }

    @SneakyThrows
    private List<String> retrieveISBNNumbers(String yearInterval, String productType, String catalog) {
        final String pageNum = "1";
        String pageSize = "2000";
        String yearIntervalSubstring = genPublicationYearIntervalRequestSubstring(yearInterval);
//        String catalogName = catalog.split("-")[0];
        String catalogNumber = catalog.split("-")[1];

//        "https://www.elsevier.com/catalog?producttype=$PRODUCT_TYPE&cat0=$CATALOG_NO&cat1=&cat2=&series=&q=&search=1&imprintname=&categoryrestriction=&page=$PAGE_NUMBER&size=$PAGE_SIZE&sort=relevance$PUB_YEAR_SUBSTRING"
//        "https://www.elsevier.com/catalog?producttype=books&cat0=27382&cat1=&cat2=&series=&q=&search=1&imprintname=&categoryrestriction=&page=1&size=2000&sort=relevance&publicationyear=1980"
        String tempBookShopUrlLink = scripts.bookShopUrlLink.replace("$PRODUCT_TYPE", productType)
                .replace("$CATALOG_NO", catalogNumber).replace("$PAGE_NUMBER", pageNum)
                .replace("$PUB_YEAR_SUBSTRING", yearIntervalSubstring);

        pageSize = getBookCount(tempBookShopUrlLink.replace("$PAGE_SIZE", pageSize));
        String preparedUrlLink = tempBookShopUrlLink.replace("$PAGE_SIZE", pageSize);

        Document doc = Jsoup.connect(preparedUrlLink)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .maxBodySize(20000000).timeout(120*1000).get();

        List<String> isbns = doc.select(".listing-section-products").select(".row.listing-products")
                .stream().map(elem -> elem.attr("data-identifier")).collect(Collectors.toList());


        return isbns;
    }

    @SneakyThrows
    private String getBookCount(String bookShopUrlLink) {
        Document doc = Jsoup.connect(bookShopUrlLink).get();
        return doc.select(".listing-form-filters-results-showing").text()
                .split("of")[1].split("Products")[0].trim();
    }

    private String genPublicationYearIntervalRequestSubstring(String interval) {
        Integer yearStart = Integer.valueOf(interval.split("-")[0]);
        Integer yearEnd = Integer.valueOf(interval.split("-")[1]);
        String result = "";
        for (int n = yearStart; n <= yearEnd; n++) {
            result = result + "&publicationyear=" + n;
        }

        return result;
    }
}
