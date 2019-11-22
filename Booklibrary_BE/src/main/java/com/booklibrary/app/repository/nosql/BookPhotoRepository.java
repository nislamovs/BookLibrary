package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.BookPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookPhotoRepository extends MongoRepository<BookPhoto, String> {

    Optional<BookPhoto> findBookPhotoByIsbn(String isbn);

}
