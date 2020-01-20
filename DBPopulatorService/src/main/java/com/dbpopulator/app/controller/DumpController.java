package com.dbpopulator.app.controller;


import com.dbpopulator.app.services.mongoDump.MongoDumpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DumpController {

    private final MongoDumpService dumpService;

    @GetMapping("/dumpdb")
    public ResponseEntity<?> dumpdb() {
        return ok(dumpService.dumpDB());
    }

    @GetMapping({"/restoredb", "/restoredb/{dump}"})
    public ResponseEntity<?> restoredb(@PathVariable("dump") Optional<String> dumpFilename) {
        return ok(dumpFilename.isPresent()
                ? dumpService.restoreDB(dumpFilename.get())
                : dumpService.restoreLast());
    }

    @GetMapping("/dumplist")
    public ResponseEntity<?> test() {
        return ok(dumpService.getDumpList());
    }
}
