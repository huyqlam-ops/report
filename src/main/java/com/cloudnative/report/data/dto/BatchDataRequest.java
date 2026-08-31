package com.cloudnative.report.data.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class BatchDataRequest {

    List<DataRequest> items;
}
