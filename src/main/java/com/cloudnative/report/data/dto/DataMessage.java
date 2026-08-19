package com.cloudnative.report.data.dto;

import lombok.Builder;

@Builder
public record DataMessage(String key, String path) {
}
