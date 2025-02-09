package ru.t1.java.demo.service.jsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.model.Transaction;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonFileTransaction {
    private final File file = new File("src/main/resources/transactions.json");

    public TransactionDTO[] arrayTransactionDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, TransactionDTO[].class);
        } catch (IOException ex){
            throw  new RuntimeException(ex);
        }
    }

    public List<Transaction> arrayaListTransaction()  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
