package com.generoso.kafka.streams.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.kafka.streams.StoreQueryParameters.fromNameAndType;

@RestController
@RequestMapping("state-store")
public class Controller {

    @Autowired
    private KafkaStreams kafkaStreams;

    @PostMapping
    public void logStateStore() {

        ReadOnlyKeyValueStore<String, String> store =
            kafkaStreams.store(fromNameAndType("titles-STATE-STORE-0000000000", QueryableStoreTypes.keyValueStore()));


        System.out.println("========== State Store Start ==========");
        int count = 0;
        try (KeyValueIterator<String, String> all = store.all()) {
            while (all.hasNext()) {
                count++;
                all.next();
            }
        }

        System.out.println("Records in the state store: " + count);
        System.out.println("========== State Store End ==========");
    }
}
