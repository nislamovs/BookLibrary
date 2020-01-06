package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookData")
public class BookData extends AbstractDocument {

    @Field(name="bookId")
    private String bookId;

    @Field(name="bookTitle")
    private String bookTitle;

    @Field(name="isbn")
    private String isbn;

    @Field(name="publisher")
    private String publisher;

    @Field(name="authors")
    private List<String> authors;

    @Field(name="categories")
    private List<Category> categories;

    @Field(name="searchInfo")
    private String searchInfo;

    @Field(name="annotation")
    private String annotation;

    @Field(name="pages")
    private int pages;

    @Field(name="publishedDate")
    private String publishedDate;

    @Field(name="language")
    private Language language;

    @Field(name="totalCount")
    private int totalCount;

    @Field(name="availableCount")
    private int availableCount;

    @Field(name="popularityRate")
    private BigDecimal popularityRate;

    @Field(name="internalPrice")
    private Money internalPrice;

    @Field(name="rawPayload")   //Response from Google book API
    private String rawPayload;

    @Field(name="bookPhoto")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name="bookPhoto")
    private BookPhoto bookPhoto;

    public static enum Category { BIOLOGY, CHEMISTRY, COMPUTER_SCIENCE, ECONOMICS, PSYCHOLOGY,
        ENGINEERING, MANAGEMENT, MATERIAL_SCIENCE, MATHEMATICS, MEDICINE, MICROBIOLOGY,
        PHARMACOLOGY, SOCIAL_SCIENCE, TOXICOLOGY, VETERINARY, PHYSICS}

    public static enum Language { EN, LV, RU, DE, FR }
}
