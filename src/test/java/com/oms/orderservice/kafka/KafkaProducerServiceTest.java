package com.oms.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.orderservice.dto.OrderEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendOrderEvent() throws JsonProcessingException {
        // Arrange
        OrderEvent orderEvent = new OrderEvent("1", "CREATED", "Order has been created");

        String eventJson = "{\"orderId\":\"1\",\"status\":\"CREATED\",\"message\":\"Order has been created\"}";

        // Mock the ObjectMapper behavior
        when(objectMapper.writeValueAsString(orderEvent)).thenReturn(eventJson);

        // Act
        kafkaProducerService.sendMessage(orderEvent);

        // Create the expected ProducerRecord
        ProducerRecord<String, String> expectedProducerRecord =
                new ProducerRecord<>("order-events", eventJson);

        // Assert that kafkaTemplate.send was called with the correct ProducerRecord
        verify(kafkaTemplate, times(1)).send(eq("order-events"), any(eventJson.getClass()));
    }
}