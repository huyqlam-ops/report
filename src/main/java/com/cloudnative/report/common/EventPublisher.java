package com.cloudnative.report.common;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final EventHubProducerClient producerClient;

    public void publishEvent(String jsonPayload) {
        EventDataBatch batch = producerClient.createBatch();
        batch.tryAdd(new EventData(jsonPayload));
        producerClient.send(batch);
    }
}
