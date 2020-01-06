package com.dbpopulator.app.clients;

import com.dbpopulator.app.configuration.FeignConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookInfoRetrievalClient", url = "https://www.googleapis.com", configuration = FeignConfiguration.class)
public interface GoogleBookApiClient {

    @GetMapping(value = "/books/v1/volumes")
    public String getBookData(@RequestParam("q") String isbn);

}
