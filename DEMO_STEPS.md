# Demo steps

* Kafka As a consumer
  * Single partition, Single Consumer
  * Single partition, multiple consumers (idle)
  * 2 partitions, Single consumer
  * 2 partitions, 2 consumer
    * Add 2 extra consumers - partition are reassigned 
    * CooperativeStickyAssignor
  * Single partition, single consumer in 2 consumer groups

* Kafka Streams
  * 2 topics, 1 consumer
  * Message saved in the RocksDB and changelog and data in the metrics
  * The metrics for the State Store (100MB)
  * Rebalancing when consumers join and leave the group
