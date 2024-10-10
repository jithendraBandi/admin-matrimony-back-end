package projects.blue_whale.converter;

//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import projects.blue_whale.entity.Transaction;

import java.util.List;

@Converter(autoApply = false)
public class TransactionItemDetailsConverter implements AttributeConverter<List<Transaction.TransactionItemDetails>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Transaction.TransactionItemDetails> transactionItemDetails) {
        try {
            return transactionItemDetails != null ? objectMapper.writeValueAsString(transactionItemDetails) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting TransactionItemDetails to String", e);
        }
    }

    @Override
    public List<Transaction.TransactionItemDetails> convertToEntityAttribute(String transactionItemDetailsString) {
        try {
            return transactionItemDetailsString != null ? objectMapper.readValue(transactionItemDetailsString,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.TransactionItemDetails.class)) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting String to TransactionItemDetails", e);
        }
    }
}

