package com.cloudnative.report.data.web;

import com.cloudnative.report.common.AudiLog;
import com.cloudnative.report.data.dto.DataRequest;
import com.cloudnative.report.data.dto.DataResponse;
import com.cloudnative.report.data.service.DataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cloudnative.report.common.Roles.ADMIN;

@AudiLog
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/data")
    @PreAuthorize("hasRole('" + ADMIN + "')")
    public void uploadData(@RequestBody @Valid DataRequest request) {
        dataService.processData(request.getKey(), request.getData());
    }

    @GetMapping("/data/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DataResponse getData(@PathVariable(name = "id") String key) {
        return dataService.retrieveData(key);
    }
}
