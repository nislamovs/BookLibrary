package com.dbpopulator.app.services.messaging;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.MDC;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static java.lang.String.format;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.joda.time.DateTime.now;
import static org.joda.time.format.ISODateTimeFormat.dateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage alertMessageTemplate;

    public void sendError(Throwable t) {
        alertMessageTemplate.setText(buildMessage(new Alert(t)));
        sendMessage(alertMessageTemplate);
    }

    @ToString
    static final class Alert {
        public final String stackTrace;
        public final DateTime timestamp;

        public Alert(Throwable t) {
            stackTrace = getStackTraceAsString(t);
            timestamp = now();
        }
    }

    private String buildMessage(Alert alert) {
        StringBuilder strBuild = new StringBuilder(1024);

        strBuild.append("\nTimestamp: ").append(dateTime().print(alert.timestamp))
                .append("\nStack trace:\n").append(alert.stackTrace).append('\n');
        return strBuild.toString();
    }

    private void sendMessage(SimpleMailMessage message) {
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("Failed to send alert email", e);
        }
    }
}
