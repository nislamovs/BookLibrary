package com.dbpopulator.app.services.mongoDump.SFTP;

import com.dbpopulator.app.domain.exceptions.DumpNotFoundException;
import com.jcraft.jsch.ChannelSftp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.filters.SftpSystemMarkerFilePresentFileListFilter;
import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class SFTPService {

    private final SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory;
    private final String DIR_PATH = "dumps/";

    @Bean
    @ServiceActivator(inputChannel = "lsChannel")
    public MessageHandler getFileList() {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "ls", "'" + DIR_PATH + "' + payload");
        sftpOutboundGateway.setOption(AbstractRemoteFileOutboundGateway.Option.NAME_ONLY);

        return sftpOutboundGateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public MessageHandler upload() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
        handler.setAutoCreateDirectory(true);
        handler.setRemoteDirectoryExpression(new LiteralExpression(DIR_PATH));

        return handler;
    }

    @Bean
    @ServiceActivator(inputChannel = "fromSftpChannel")
    public MessageHandler fileExistanceChecker() {
        return message -> {
            SftpRemoteFileTemplate requestedFile = new SftpRemoteFileTemplate(sftpSessionFactory);
            if (!requestedFile.exists(message.getPayload().toString()))
                throw new DumpNotFoundException(format("Requested dump [%s] not found!", message.getPayload().toString()));
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fromSftpChannel")
    public MessageHandler downloadDump() {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "get", "'" + DIR_PATH + "' + payload");
        sftpOutboundGateway.setOption(AbstractRemoteFileOutboundGateway.Option.STREAM);

        return sftpOutboundGateway;
    }
}
