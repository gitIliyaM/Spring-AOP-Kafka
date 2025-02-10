package ru.t1.java.demo.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.adminKafka.*;
import ru.t1.java.demo.dto.AccountDTO;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.KafkaConsumerProcessorService;
import ru.t1.java.demo.service.KafkaProducerProcessorService;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaClient {

    private final CreateTopicPartition createTP;
    private final DeleteTopicPartition deleteTP;
    private final KafkaProducerProcessorService kafkaProducerProcessorService;
    private final KafkaConsumerProcessorService kafkaConsumerProcessorService;

    @Autowired
    public KafkaClient(
        CreateTopicPartition createTP,
        DeleteTopicPartition deleteTP,
        KafkaProducerProcessorService kafkaProducerProcessorService,
        KafkaConsumerProcessorService kafkaConsumerProcessorService
    ){
        this.createTP = createTP;
        this.deleteTP = deleteTP;
        this.kafkaProducerProcessorService = kafkaProducerProcessorService;
        this.kafkaConsumerProcessorService = kafkaConsumerProcessorService;
    }
    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(
        @RequestParam String topicName,
        @RequestParam int partitions,
        @RequestParam short replicas){
        return new ResponseEntity<>(createTP.createPostTopic(topicName, partitions, replicas), HttpStatus.OK);
    }
    @DeleteMapping("/delete-topic/{topicName}")
    public ResponseEntity<String> deleteTopic(@PathVariable String topicName){
        return new ResponseEntity<>(deleteTP.deletePostTopic(topicName), HttpStatus.OK);
    }
    @GetMapping("/get-account-dto")
    public ResponseEntity<List<AccountDTO>> getAccountDTO() {
        return new ResponseEntity<>(kafkaProducerProcessorService.kafkaAccountDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("get-account-file")
    public ResponseEntity<List<Account>> createAccountFromFile()  {
        List<Account> accountList = kafkaProducerProcessorService.kafkaAccountFromFile();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @PostMapping("/post-account")
    public ResponseEntity<List<Account>> postAccountList(@RequestBody String account)  {
        List<Account> accountList = kafkaProducerProcessorService.kafkaAccountPost(account);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @GetMapping("/get-tx-dto")
    public ResponseEntity<List<TransactionDTO>> getTransactionDTO() {
        return new ResponseEntity<>(kafkaProducerProcessorService.kafkaTransactionDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("get-tx-file")
    public ResponseEntity<List<Transaction>> createTransactionFromFile()  {
        List<Transaction> transactionList = kafkaProducerProcessorService.kafkaTransactionFromFile();
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @PostMapping("/post-tx")
    public ResponseEntity<List<Transaction>> postTransactionList(@RequestBody String transaction) {
        List<Transaction> transactionList = kafkaProducerProcessorService.kafkaTransactionPost(transaction);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @GetMapping("/get-account-string")
    public ResponseEntity<String> getStringAccountConsumer(){
        String stringConsumerRecords = kafkaConsumerProcessorService.t1DemoAccountsConsumer();
        return new ResponseEntity<>(stringConsumerRecords, HttpStatus.OK);
    }
    @GetMapping("/get-tx-string")
    public ResponseEntity<String> getStringTransactionConsumer(){
        String stringConsumerRecords = kafkaConsumerProcessorService.t1DemoATransactionConsumer();
        return new ResponseEntity<>(stringConsumerRecords, HttpStatus.OK);
    }
}
