package com.cloudnative.report.data.service.impl;

import com.cloudnative.report.common.BlobService;
import com.cloudnative.report.common.EventPublisher;
import com.cloudnative.report.common.V1Exception;
import com.cloudnative.report.data.dto.DataMessage;
import com.cloudnative.report.data.dto.DataResponse;
import com.cloudnative.report.data.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final BlobService blobService;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public void processData(String key, String data) {
        String blobName = buildBlobName(key);
        blobService.upload(blobName, data);

        try {
            DataMessage message = DataMessage.builder()
                    .key(key)
                    .path(blobName)
                    .build();
            eventPublisher.publishEvent(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new V1Exception(INTERNAL_SERVER_ERROR, "Failed to serialize event payload");
        }
    }

    @Override
    public DataResponse retrieveData(String key) {
        String blobName = buildBlobName(key);
        String data = blobService.getData(blobName);
        return DataResponse.builder()
                .key(key)
                .data(data)
                .build();
    }

    private String buildBlobName(String key) {
        return "data/%s.txt".formatted(key);
    }
}
