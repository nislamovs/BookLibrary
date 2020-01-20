package com.dbpopulator.app.services.mongoDump.SFTP;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@MessagingGateway
public interface SFTPServerGateway {

    @Gateway(requestChannel = "toSftpChannel")
    void upload(File file);

    @Gateway(requestChannel = "fromSftpChannel")
    InputStream download(String filename);

    @Gateway(requestChannel = "lsChannel")
    List<String> filelist(String directory);
}
