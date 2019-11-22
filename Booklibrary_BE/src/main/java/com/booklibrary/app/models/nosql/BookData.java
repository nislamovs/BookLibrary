package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String bookId;

    private String bookTitle;

    private String isbn;

    private String publisher;

    private List<String> authors;

    private List<Category> categories;

    private String searchInfo;

    private String annotation;

    private int pages;

    private Date publishedDate;

    private Language language;

    private int totalCount;

    private int availableCount;

    private BigDecimal popularityRate;

    private Money internalPrice;

    @OneToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinProperty(name="bookPhotos")
    private BookPhoto bookPhoto;

    public static enum Category { BIOLOGY, CHEMISTRY, COMPUTER_SCIENCE, ECONOMICS,
        ENGINEERING, MANAGEMENT, MATERIAL_SCIENCE, MATHEMATICS, MEDICINE, MICROBIOLOGY,
        PHARMACOLOGY, SOCIAL_SCIENCE, TOXICOLOGY, VETERINARY}

    public static enum Language { EN, LV, RU, DE, FR }

}
