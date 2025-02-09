package ru.t1.java.demo.controller.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.java.demo.service.DataProcessorService;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.*;
import ru.t1.java.demo.model.*;
import org.springframework.http.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountClient {

    private final DataProcessorService dataProcessorService;

    @Autowired
    public AccountClient(DataProcessorService dataProcessorService){
        this.dataProcessorService = dataProcessorService;

    }
    @GetMapping("/get-account-dto")
    public ResponseEntity<List<AccountDTO>> getAccountDTO()  {
        return new ResponseEntity<>(dataProcessorService.createAccountDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("get-account-file")
    public ResponseEntity<List<Account>> createAccountFromFile()  {
        List<Account> accountList = dataProcessorService.createAccountFromFile();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @GetMapping("/get-tx-dto")
    public ResponseEntity<List<TransactionDTO>> getTransactionDTO() {
        return new ResponseEntity<>(dataProcessorService.createTransactionDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("get-tx-file")
    public ResponseEntity<List<Transaction>> createTransactionFromFile()  {
        List<Transaction> transactionList = dataProcessorService.createTransactionFromFile();
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @PostMapping("/post-account")
    public ResponseEntity<List<Account>> postAccountList(@RequestBody String account)  {
        List<Account> accountList = dataProcessorService.createAccountPost(account);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @PostMapping("/post-tx")
    public ResponseEntity<List<Transaction>> postTransactionList(@RequestBody String transaction) {
        List<Transaction> transactionList = dataProcessorService.createTransactionPost(transaction);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }


}
