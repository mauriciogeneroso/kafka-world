package com.generoso.kafka.streams;

import com.generoso.kafka.streams.config.RocksDbConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.kafka.KafkaStreamsMetrics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.StoreBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.streams.state.Stores.keyValueStoreBuilder;
import static org.apache.kafka.streams.state.Stores.persistentKeyValueStore;

@Configuration
public class StreamProcessor {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaStreams kafkaStreams(MeterRegistry meterRegistry) {
        StreamsBuilder builder = new StreamsBuilder();
        KTable<String, String> table1 = builder.table("titles", Consumed.with(Serdes.String(), Serdes.String()));
        KTable<String, String> table2 = builder.table("rights", Consumed.with(Serdes.String(), Serdes.String()));

        table1.toStream().peek((k, v) -> System.out.println("Received key : " + k + " value : " + v));
        table2.toStream().peek((k, v) -> System.out.println("Received key : " + k + " value : " + v));

        KTable<String, String> joinedTable = table1.join(table2,
            (value1, value2) -> value1 + "-" + value2,
            Materialized.with(Serdes.String(), Serdes.String())
        );

        joinedTable.toStream().foreach((k, v) ->
            System.out.println("merged key : " + k + " value : " + v));

        KafkaStreams streams = new KafkaStreams(builder.build(), properties());
        new KafkaStreamsMetrics(streams).bindTo(meterRegistry);
        streams.start();
        return streams;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-example");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(StreamsConfig.TOPOLOGY_OPTIMIZATION_CONFIG, StreamsConfig.OPTIMIZE);
//        properties.put(StreamsConfig.STATESTORE_CACHE_MAX_BYTES_CONFIG, 0);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 17_000);
//        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10_000_000);
//        properties.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, RocksDbConfig.class);
        return properties;
    }
}
