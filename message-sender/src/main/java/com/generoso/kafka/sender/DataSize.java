package com.generoso.kafka.sender;

public record DataSize(long size, Unit unit) {
    enum Unit {MB, GB}

    public long toBytes() {
        return switch (unit) {
            case MB -> size * 1024 * 1024;
            case GB -> size * 1024 * 1024 * 1024;
        };
    }
}
