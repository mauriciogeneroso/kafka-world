package com.generoso.kafka.sender;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSender {

    private static final int NUM_THREADS = 10;
    private static final long MESSAGE_LENGTH = 500 - (long) 2; // 2 is used in the empty header {}
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator((int) MESSAGE_LENGTH);

    private MessageSender() {
    }

    public static void send(String topicName, DataSize dataSize) {
        try (KafkaProducer<String, String> kafkaProducer = DefaultProducer.create()) {
            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

            long numMessages = dataSize.toBytes() / MESSAGE_LENGTH;
            System.out.printf("===== Sending %s messages using %d threads =====\n", numMessages, NUM_THREADS);

            long[] messagesPerThread = messagesPerThread(numMessages);
            for (int i = 0; i < messagesPerThread.length; i++) {
                var threadId = i * dataSize.size();
                var currentThreadMessages = messagesPerThread[i];
                executor.submit(() -> {
                    for (var j = 0; j < currentThreadMessages; j++) {
                        var key = "key" + (threadId * currentThreadMessages + j);
                        var value = STRING_GENERATOR.generate(key.length());
                        kafkaProducer.send(new ProducerRecord<>(topicName, key, value), callback());
                    }
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Wait for all threads to finish
            }
            System.out.println("===== 100% messages sent =====");
        }
    }

    private static long[] messagesPerThread(long numMessages) {
        long messagesPerThread = numMessages / NUM_THREADS;
        long extraMessages = numMessages % NUM_THREADS;
        long[] messagesPerThreadCounter = new long[NUM_THREADS];
        for (var i = 0; i < NUM_THREADS; i++) {
            if (i == NUM_THREADS - 1) {
                messagesPerThreadCounter[i] = messagesPerThread + extraMessages;
            } else {
                messagesPerThreadCounter[i] = messagesPerThread;
            }
        }

        return messagesPerThreadCounter;
    }

    private static org.apache.kafka.clients.producer.Callback callback() {
        return (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }
        };
    }
}
