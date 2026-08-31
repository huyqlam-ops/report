package com.cloudnative.report.common;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final EventHubProducerClient producer;

    public void publishEvent(String jsonPayload) {
        EventDataBatch eventDataBatch = producer.createBatch();
        EventData eventData = new EventData(jsonPayload);

        if (!eventDataBatch.tryAdd(eventData)) {
            throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                    + eventDataBatch.getMaxSizeInBytes());
        }

        producer.send(eventDataBatch);
        log.info("Successfully published event to Event Hub: {}", jsonPayload);
    }
}
