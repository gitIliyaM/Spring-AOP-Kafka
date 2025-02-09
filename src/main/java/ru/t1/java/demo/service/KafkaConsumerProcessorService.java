package ru.t1.java.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.buildBeansTopicsKafka.BuildBeansTopicsKafkaConfig;
import ru.t1.java.demo.configuration.kafkaConsumer.KafkaCreateConsumer;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class KafkaConsumerProcessorService {

    private final KafkaCreateConsumer kafkaCreateConsumer;
    private final BuildBeansTopicsKafkaConfig kafkaTopics;

    @Autowired
    KafkaConsumerProcessorService(
        KafkaCreateConsumer kafkaCreateConsumer,
        BuildBeansTopicsKafkaConfig kafkaTopics
    )
    {
        this.kafkaCreateConsumer = kafkaCreateConsumer;
        this.kafkaTopics = kafkaTopics;
    }

    public String t1DemoAccountsConsumer(){
        KafkaConsumer<String, String> kafkaConsumer = kafkaCreateConsumer.getKafkaConsumer();
        kafkaConsumer.subscribe(Pattern.compile(kafkaTopics.t1_demo_accounts().name()));


        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        Map<String,String> map = new HashMap<>();
        for(ConsumerRecord<String, String> consumerRecord: consumerRecords(kafkaConsumer)){
            map.put(consumerRecord.key(),consumerRecord.value());
            try {
                jsonString = mapper.writeValueAsString(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return jsonString;
    }
    public String t1DemoATransactionConsumer(){
        KafkaConsumer<String, String> kafkaConsumer = kafkaCreateConsumer.getKafkaConsumer();
        kafkaConsumer.subscribe(Pattern.compile(kafkaTopics.t1_demo_transactions().name()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        Map<String,String> map = new HashMap<>();
        for(ConsumerRecord<String, String> consumerRecord: consumerRecords(kafkaConsumer)){
            map.put(consumerRecord.key(),consumerRecord.value());
            try {
                jsonString = mapper.writeValueAsString(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return jsonString;
    }
    public ConsumerRecords<String, String> consumerRecords(KafkaConsumer<String, String> kafkaConsumer){
        return kafkaConsumer.poll(Duration.ofMillis(100));
    }
}
