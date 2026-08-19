package com.cloudnative.report.data.service;

import com.cloudnative.report.data.dto.DataResponse;

public interface DataService {

    void processData(String key, String data);

    DataResponse retrieveData(String key);
}
