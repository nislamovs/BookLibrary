package com.dbpopulator.app.services.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.ZonedDateTime.now;

@Slf4j
@Service
public class SlackService {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    OkHttpClient slackCall;

    @Autowired
    Request slackRequest;

    @SneakyThrows
    public void pushMessage(Object object) {
        checkNotNull(object, "Slack message not set!");

        String msg = mapper.writeValueAsString(new SlackMessageTemplate(object.toString()));

        RequestBody reqBody = RequestBody.create(msg.getBytes());
        Request req = slackRequest.newBuilder().post(reqBody).build();

        slackCall.newCall(req).execute().close();
    }

    public static String TIMESTAMP() {
        return now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    };
}
