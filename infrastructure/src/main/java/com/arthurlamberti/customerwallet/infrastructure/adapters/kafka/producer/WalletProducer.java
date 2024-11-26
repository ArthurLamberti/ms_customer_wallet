package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.producer;

import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.config.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WalletProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, UpdateWalletResponse response) {
        kafkaTemplate.send(topic, key, Json.writeValueAsString(response));
    }
}
