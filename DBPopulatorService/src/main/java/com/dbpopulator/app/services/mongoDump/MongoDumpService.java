package com.dbpopulator.app.services.mongoDump;

import com.dbpopulator.app.domain.exceptions.DumpNotFoundException;
import com.dbpopulator.app.domain.responses.DumpResponse;
import com.dbpopulator.app.services.components.ShellScriptExecutor;
import com.dbpopulator.app.services.mongoDump.SFTP.SFTPServerGateway;
import com.dbpopulator.app.services.mongoDump.SFTP.SFTPService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Collections.reverseOrder;

@Service
@Slf4j
@RequiredArgsConstructor
public class MongoDumpService {

    private final ShellScriptExecutor executor;
    private final ShellScripts shellScripts;
    private final SFTPServerGateway sftpServerGateway;
    private final SFTPService sftpService;

    private final String DUMP_PATH = "/opt/backup/";
    private final String DUMP_PREFIX = "booklibrary_dump_";
    private final String DUMP_POSTFIX = ".tar.gz";

    public DumpResponse dumpDB() {
        String datetime = LocalDateTime.now().format(ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String dumpFullFilename = DUMP_PATH + DUMP_PREFIX + datetime + DUMP_POSTFIX;

        executor.executeCommand((shellScripts.dumpScript + dumpFullFilename).split(" "));
        File file = new File(dumpFullFilename);
        sftpServerGateway.upload(file);
        cleanupDumpFolder();

        return new DumpResponse(DUMP_PREFIX + datetime + DUMP_POSTFIX, "Database dumped.", datetime);
    }

    @SneakyThrows
    public DumpResponse restoreDB(String filename) {
        String datetime = LocalDateTime.now().format(ofPattern("yyyy-MM-dd_HH:mm:ss"));
        File targetFile = new File(DUMP_PATH + datetime + "_" + filename);
        FileUtils.copyInputStreamToFile(sftpServerGateway.download(filename), targetFile);

        executor.executeCommand((shellScripts.restoreScript + targetFile).split(" "));
        cleanupDumpFolder();

        return new DumpResponse(filename, "Database restored.", datetime);
    }

    public DumpResponse restoreLast() {
        return restoreDB(getLastDumpFilename());
    }

    public List<String> getDumpList() {
        return sftpServerGateway.filelist("");
    }

    private String getLastDumpFilename() {
        List<String> dumpList = Optional.ofNullable(getDumpList())
                .orElseThrow(() -> new DumpNotFoundException("No dumps found!"));

        String lastDumpDateTime = dumpList.stream()
                .filter(value -> value.endsWith(DUMP_POSTFIX))
                .map(value -> value.split(DUMP_POSTFIX)[0])
                .map(value -> value.split(DUMP_PREFIX)[1])
                .map(value -> LocalDateTime.parse(value, ofPattern("yyyy-MM-dd_HH:mm:ss")))
                .sorted(reverseOrder())
                .map(value -> value.format(ofPattern("yyyy-MM-dd_HH:mm:ss")))
                .findFirst().get();

        return dumpList.stream().filter(value -> value.contains(lastDumpDateTime)).findFirst().get();
    }

    @SneakyThrows
    private void cleanupDumpFolder() {
        FileUtils.cleanDirectory(new File(DUMP_PATH));
    }
}
