package com.booklibrary.app.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController  {

    @GetMapping
    public ResponseEntity<?> getAllUserList() {

        log.info("Retrieving user list with all loans and extentions.");

        return new ResponseEntity<String>("asdasddasd", HttpStatus.OK);
    }


}
