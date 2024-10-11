package projects.blue_whale.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Item;
import projects.blue_whale.entity.Transaction;
import projects.blue_whale.repository.ItemRepository;
import projects.blue_whale.repository.TransactionRepository;
import projects.blue_whale.service.TransactionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        List<Item> itemsList = itemRepository.findAll();
        if (transaction.getId() != null) {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getId());
            if (optionalTransaction.isEmpty()) {
                throw new RuntimeException("Transaction not exist with given id: " + transaction.getId());
            }
            Transaction existingTransaction = optionalTransaction.get();
            if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.BUY) && transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
                removeAndAddQuantity(existingTransaction.getTransactionItemDetails(), transaction.getTransactionItemDetails(), itemsList);
            }
            else if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.SELL) && transaction.getTradeType().equalsIgnoreCase(Constants.SELL)) {
                removeAndAddQuantity(transaction.getTransactionItemDetails(), existingTransaction.getTransactionItemDetails(), itemsList);
            }
            else if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.BUY) && transaction.getTradeType().equalsIgnoreCase(Constants.SELL)) {
                List<Transaction.TransactionItemDetails> itemDetails = concatItemDetails(existingTransaction, transaction);
                updateQuantity(itemDetails, itemsList, Constants.SELL);
            }
            else if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.SELL) && transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
                List<Transaction.TransactionItemDetails> itemDetails = concatItemDetails(existingTransaction, transaction);
                updateQuantity(itemDetails, itemsList, Constants.BUY);
            }
            else {
                throw new RuntimeException("Invalid Trade Type");
            }
        }
        else {
            if (transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
                updateQuantity(transaction.getTransactionItemDetails(), itemsList, Constants.BUY);
            }
            else if (transaction.getTradeType().equalsIgnoreCase(Constants.SELL)) {
                updateQuantity(transaction.getTransactionItemDetails(), itemsList, Constants.SELL);
            }
            else {
                throw new RuntimeException("Invalid Trade Type");
            }
        }

        itemRepository.saveAll(itemsList);
        transactionRepository.save(transaction);
    }

    private List<Transaction.TransactionItemDetails> concatItemDetails(Transaction existingTransaction, Transaction transaction) {
        return Stream.concat(
                        existingTransaction.getTransactionItemDetails().stream(),
                        transaction.getTransactionItemDetails().stream()
                )
                .toList();
    }

    private void updateQuantity(List<Transaction.TransactionItemDetails> itemDetails, List<Item> itemsList, String tradeType) {
        List<Long> itemDetailsIds = itemDetails.stream().map(Transaction.TransactionItemDetails::getItemId).toList();
        Map<Long, Integer> itemDetailsIdQuantityMap = itemDetails.stream()
                .collect(Collectors.toMap(
                        Transaction.TransactionItemDetails::getItemId,
                        Transaction.TransactionItemDetails::getQuantity,
                        Integer::sum
                ));
        if (tradeType.equalsIgnoreCase(Constants.BUY)) {
            itemsList.forEach(item -> {
                if (itemDetailsIds.contains(item.getId())) {
                    item.setQuantity(item.getQuantity() + itemDetailsIdQuantityMap.get(item.getId()));
                }
            });
        }
        else if (tradeType.equalsIgnoreCase(Constants.SELL)) {
            itemsList.forEach(item -> {
                if (itemDetailsIds.contains(item.getId())) {
                    item.setQuantity(item.getQuantity() - itemDetailsIdQuantityMap.get(item.getId()));
                }
            });
        }
        else {
            throw new RuntimeException("Invalid Trade Type");
        }
    }

    private void removeAndAddQuantity(List<Transaction.TransactionItemDetails> removeTransactionItemDetails, List<Transaction.TransactionItemDetails> addTransactionItemDetails, List<Item> itemsList) {
        updateQuantity(removeTransactionItemDetails, itemsList, Constants.SELL);
        updateQuantity(addTransactionItemDetails, itemsList, Constants.BUY);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();
        Collections.reverse(transactionList);
        return transactionList;
    }
}
