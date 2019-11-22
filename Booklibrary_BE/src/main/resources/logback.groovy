import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

//===================================================================//
//                 BOOK LIBRARY STANDART LOGGING                     //
//===================================================================//

appender("STANDART_LOG", RollingFileAppender) {
    file = 'logs/Booklibrary.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/Booklibrary/Booklibrary_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("STANDART_ASYNC_LOG", AsyncAppender) {
    appenderRef("STANDART_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//===================================================================//
//                      HIBERNATE REQUESTS                           //
//===================================================================//

appender("HIBERNATE_LOG", RollingFileAppender) {
    file = 'logs/DBRequests.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/DBRequests/DBRequests_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("HIBERNATE_ASYNC_LOG", AsyncAppender) {
    appenderRef("HIBERNATE_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//Hibernate
logger("org.hibernate.SQL", DEBUG, ["HIBERNATE_ASYNC_LOG"])
logger("org.hibernate.type.descriptor.sql.BasicBinder", TRACE, ["HIBERNATE_ASYNC_LOG"])

//===================================================================//
//                      MONGODB REQUESTS                             //
//===================================================================//

appender("MONGODB_LOG", RollingFileAppender) {
    file = 'logs/MongoDBRequests.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/MongoDBRequests/MongoDBRequests_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("MONGODB_ASYNC_LOG", AsyncAppender) {
    appenderRef("MONGODB_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//MongoDB
logger("org.springframework.data.mongodb.core.MongoTemplate", DEBUG, ["MONGODB_ASYNC_LOG"], false)

//===================================================================//
//                      TOMCAT LOGGING                               //
//===================================================================//

appender("TOMCAT_LOG", RollingFileAppender) {
    file = 'logs/Tomcat.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/TomcatLogs/Tomcat_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("TOMCAT_ASYNC_LOG", AsyncAppender) {
    appenderRef("TOMCAT_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//Tomcat
logger("org.apache.tomcat", DEBUG, ["TOMCAT_ASYNC_LOG"], false)
logger("org.apache.catalina", DEBUG, ["TOMCAT_ASYNC_LOG"], false)

//===================================================================//
//                          LIQUIBASE LOGGING                        //
//===================================================================//

appender("LIQUIBASE_LOG", RollingFileAppender) {
    file = 'logs/Liquibase.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/LiquibaseLogs/Liquibase_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("LIQUIBASE_ASYNC_LOG", AsyncAppender) {
    appenderRef("LIQUIBASE_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//Liquibase
logger("liquibase", INFO, ["LIQUIBASE_ASYNC_LOG"], false)

//===================================================================//
//                          MONGOCK LOGGING                          //
//===================================================================//

appender("MONGOCK_LOG", RollingFileAppender) {
    file = 'logs/Mongock.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/MongoCKLogs/Mongock_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("MONGOCK_ASYNC_LOG", AsyncAppender) {
    appenderRef("MONGOCK_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//MongoCK
logger("com.github.cloudyrock.mongock", DEBUG, ["MONGOCK_ASYNC_LOG"], false)
logger("org.mongodb", DEBUG, ["MONGOCK_ASYNC_LOG"], false)

//===================================================================//
//                       TRANSACTIONS LOGGING                        //
//===================================================================//

appender("TRANSACTIONS_LOG", RollingFileAppender) {
    file = 'logs/Transactions.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/TransactionsLogs/Transactions_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("TRANSACTIONS_ASYNC_LOG", AsyncAppender) {
    appenderRef("TRANSACTIONS_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//Transactions
logger("org.springframework.orm.jpa", DEBUG, ["TRANSACTIONS_ASYNC_LOG"])
logger("org.springframework.transaction", DEBUG, ["TRANSACTIONS_ASYNC_LOG"])

//===================================================================//
//                        REQUESTS / RESPONSES                       //
//===================================================================//

appender("REQUESTS_RESPONSES_LOG", RollingFileAppender) {
    file = 'logs/RequestsResponses.log'
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'logs/RequestsResponsesLogs/RequestsResponses_%d{dd-MM-yyyy}-%i.log.zip'
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "100MB"
        }
        maxHistory = 7
    }
}

appender("REQUESTS_RESPONSES_ASYNC_LOG", AsyncAppender) {
    appenderRef("REQUESTS_RESPONSES_LOG")
    includeCallerData = true
    queueSize = 2000
    discardingThreshold = 0
}

//Requests/Responses
//logger("org.apache.catalina.filters.RequestDumperFilter", DEBUG, ["REQUESTS_RESPONSES_ASYNC_LOG"])
logger("org.springframework.web.servlet.DispatcherServlet", DEBUG, ["REQUESTS_RESPONSES_ASYNC_LOG"])
logger("org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor", DEBUG, ["REQUESTS_RESPONSES_ASYNC_LOG"])

//===================================================================//
//                              STDOUT                               //
//===================================================================//

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d{"yyyy-MM-dd HH:mm:ss,SSS", Europe/Helsinki} %-5p [%t] %c - %msg%n'
    }
}

//===================================================================//
//org.apache.catalina.filters.RequestDumperFilter

logger("org.springframework.web.filter.CommonsRequestLoggingFilter", DEBUG, ["STANDART_ASYNC_LOG"])

root(INFO, ["STANDART_ASYNC_LOG", "STDOUT"])
