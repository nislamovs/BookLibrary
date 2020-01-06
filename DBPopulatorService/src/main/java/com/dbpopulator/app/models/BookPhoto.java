package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookPhotos")
public class BookPhoto extends AbstractDocument {

    @Field(name="bookPhotoId")
    private String bookPhotoId;

    @Field(name="isbn")
    private String isbn;

    @Field(name="bookPhoto")
    private String bookPhoto;

}
