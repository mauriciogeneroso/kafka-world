package com.generoso.kafka.sender;

public class App {

    private static final String TOPIC_NAME = "example-topic";
    private static final DataSize SIZE = new DataSize(100, DataSize.Unit.MB);

    public static void main(String[] args) {
        MessageSender.send(TOPIC_NAME, SIZE);
    }
}
