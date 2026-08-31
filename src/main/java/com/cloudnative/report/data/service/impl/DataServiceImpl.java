package com.cloudnative.report.data.service.impl;

import com.cloudnative.report.common.BlobService;
import com.cloudnative.report.common.EventPublisher;
import com.cloudnative.report.common.V1Exception;
import com.cloudnative.report.data.dto.BatchDataRequest;
import com.cloudnative.report.data.dto.DataMessage;
import com.cloudnative.report.data.dto.DataResponse;
import com.cloudnative.report.data.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private static final String DATA_DIR = "data";
    private static final String BATCH_DIR = "batch_raw_data";

    private final BlobService blobService;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public void processData(String key, String data) {
        String blobName = buildBlobName(DATA_DIR, key);
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
        String blobName = buildBlobName(DATA_DIR, key);
        String data = blobService.getData(blobName);
        return DataResponse.builder()
                .key(key)
                .data(data)
                .build();
    }

    @Override
    public void batchProcessData(BatchDataRequest batchData) {
        if (Objects.isNull(batchData) || Objects.isNull(batchData.getItems())
                || batchData.getItems().isEmpty()) {
            throw new V1Exception(BAD_REQUEST, "Batch data request must not be null or empty");
        }

        String blobName = buildBlobName(BATCH_DIR, "batch_" + System.currentTimeMillis());
        try {
            String batchDataJson = objectMapper.writeValueAsString(batchData);
            blobService.upload(blobName, batchDataJson);
        } catch (JsonProcessingException e) {
            throw new V1Exception(INTERNAL_SERVER_ERROR, "Failed to serialize event payload");
        }
    }

    private String buildBlobName(String dir, String key) {
        return "%s/%s.txt".formatted(dir, key);
    }
}
