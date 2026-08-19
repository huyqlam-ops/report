package com.cloudnative.report.data.dto;

import lombok.Builder;

@Builder
public record DataResponse(String key, String data) {
}
