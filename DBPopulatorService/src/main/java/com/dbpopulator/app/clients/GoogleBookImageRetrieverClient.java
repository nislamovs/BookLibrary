package com.dbpopulator.app.clients;

import com.dbpopulator.app.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookPhotoRetrievalClient", url = "http://books.google.com", configuration = FeignConfiguration.class)
public interface GoogleBookImageRetrieverClient {

    @GetMapping(value = "/books/content?printsec=frontcover&img=1&zoom=1&source=gbs_api")
    public byte[] getBookPhoto(@RequestParam(value = "id") String id);

}
