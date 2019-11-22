package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IReportController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController implements IReportController {
}
