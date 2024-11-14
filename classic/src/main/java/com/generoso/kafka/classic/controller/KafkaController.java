package com.generoso.kafka.classic.controller;

//
//@RestController
//public class KafkaController {
//
//    @Autowired
//    private KafkaProducer kafkaProducer;
//
//    @GetMapping("/send")
//    public String sendMessageToKafka(@RequestParam("message") String message) {
//        kafkaProducer.sendMessage(message);
//        return "Message sent to Kafka!";
//    }
//}
//
//
//@Service
//class KafkaProducer {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(String message) {
//        System.out.println("Producing message: " + message);
//        kafkaTemplate.send(TOPIC_NAME, message);
//    }
//}
