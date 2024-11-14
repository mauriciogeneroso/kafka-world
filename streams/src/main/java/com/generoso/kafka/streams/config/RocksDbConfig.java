package com.generoso.kafka.streams.config;

import org.apache.kafka.streams.state.RocksDBConfigSetter;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;

import java.time.Duration;
import java.util.Map;

public class RocksDbConfig implements RocksDBConfigSetter {

    @Override
    public void setConfig(String storeName, Options options, Map<String, Object> configs) {
        options.setCompactionStyle(CompactionStyle.LEVEL);
        options.setCompressionType(CompressionType.LZ4_COMPRESSION);
        options.setBottommostCompressionType(CompressionType.ZSTD_COMPRESSION);
        options.setLevelCompactionDynamicLevelBytes(true);
        options.setPeriodicCompactionSeconds(Duration.ofDays(1).toSeconds());

        options.setMaxWriteBufferNumber(3);             // (default is 2) the number of memtables before write the data in disk
        options.setWriteBufferSize(32L * 1024L * 1024L);   // (64 Mb is default) Set a 16MB write buffer size
    }

    @Override
    public void close(String storeName, Options options) {
    }
}
