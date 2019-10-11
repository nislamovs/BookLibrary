import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("FILE", RollingFileAppender) {
    file = 'logs/Booklibrary.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'Booklibrary_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "10MB"
        }
        maxHistory = 7
    }
}

appender("FILE-ASYNC", AsyncAppender) {
    appenderRef("FILE")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
}

root(INFO, ["FILE-ASYNC", "STDOUT"])
logger("Booklibrary", DEBUG)
logger("org.springframework.web.filter.CommonsRequestLoggingFilter", DEBUG)
