package com.cloudnative.report.data.web;

import com.cloudnative.report.common.AudiLog;
import com.cloudnative.report.data.dto.BatchDataRequest;
import com.cloudnative.report.data.dto.DataRequest;
import com.cloudnative.report.data.dto.DataResponse;
import com.cloudnative.report.data.service.DataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.cloudnative.report.common.Roles.ADMIN;

@AudiLog
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/data")
    @Secured(ADMIN)
    public void uploadData(@RequestBody @Valid DataRequest request) {
        dataService.processData(request.getKey(), request.getData());
    }

    @PostMapping("/data/batch")
    @Secured(ADMIN)
    public void uploadBatchData(@RequestBody @Valid BatchDataRequest request) {
        dataService.batchProcessData(request);
    }

    @GetMapping("/debug/throw-500")
    public void throwError() {
        throw new RuntimeException("Test 500 for alert - remove after testing");
    }

    @GetMapping("/data/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DataResponse getData(@PathVariable(name = "id") String key) {
        return dataService.retrieveData(key);
    }
}
