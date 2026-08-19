package com.cloudnative.report.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DataRequest {
    @NotBlank
    String key;

    @NotBlank
    String data;
}
