package com.dbpopulator.app.clients;

import com.dbpopulator.app.domain.exceptions.UserRateLimitExceededException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == HttpStatus.FORBIDDEN.value())
            throw new UserRateLimitExceededException("User Rate Limit Exceeded. Wait a bit (~30 min) and try again later :)");
        return new Exception(response.reason());
    }
}
