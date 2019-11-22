package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookPhotos")
public class BookPhoto extends AbstractDocument {

    private String bookPhotoId;

    private String isbn;

    private String bookPhoto;

}
