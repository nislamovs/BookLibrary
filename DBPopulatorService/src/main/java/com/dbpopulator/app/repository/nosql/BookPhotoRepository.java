package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.BookPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookPhotoRepository extends MongoRepository<BookPhoto, String> {



}
