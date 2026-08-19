package com.cloudnative.report.common;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class BlobService {

    private final BlobContainerClient blobContainerClient;

    public void upload(String blobName, String content) {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        if (blobClient.exists()) {
            throw new V1Exception(BAD_REQUEST, "Data with blobName " + blobName + " already exists.");
        }
        blobClient.upload(BinaryData.fromString(content), true);
    }

    public String getData(String blobName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        if (!blobClient.exists()) {
            throw new V1Exception(BAD_REQUEST, "Data with blobName " + blobName + " does not exist.");
        }
        return blobClient.downloadContent().toString();
    }
}
