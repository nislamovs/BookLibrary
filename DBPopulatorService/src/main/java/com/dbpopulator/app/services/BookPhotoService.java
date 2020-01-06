package com.dbpopulator.app.services;

import com.dbpopulator.app.services.messaging.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookPhotoService {

    private static final String SERVICE_NAME = "BookPhotoService";
    private final SlackService slackService;

}
