package projects.blue_whale.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Item;
import projects.blue_whale.entity.Transaction;
import projects.blue_whale.repository.ItemRepository;
import projects.blue_whale.repository.TransactionRepository;
import projects.blue_whale.service.TransactionService;
import projects.exceptions.CustomException;

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
    @Transactional
    public void saveTransaction(Transaction transaction) {
        List<Item> itemsList = itemRepository.findAll();
        if (transaction.getId() != null) {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getId());
            if (optionalTransaction.isEmpty()) {
                throw new CustomException("Transaction not exist with given id: " + transaction.getId());
            }
            Transaction existingTransaction = optionalTransaction.get();
            if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.BUY) && transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
                removeAndAddQuantity(existingTransaction.getTransactionItemDetails(), transaction.getTransactionItemDetails(), itemsList);
            }
            else if (
                    (
                            existingTransaction.getTradeType().equalsIgnoreCase(Constants.SELL)
                    || existingTransaction.getTradeType().equalsIgnoreCase(Constants.USAGE)

                    ) && (
                    transaction.getTradeType().equalsIgnoreCase(Constants.SELL) ||
                            transaction.getTradeType().equalsIgnoreCase(Constants.USAGE)
                    )

            ) {
                removeAndAddQuantity(transaction.getTransactionItemDetails(), existingTransaction.getTransactionItemDetails(), itemsList);
            }
            else if (existingTransaction.getTradeType().equalsIgnoreCase(Constants.BUY) && (transaction.getTradeType().equalsIgnoreCase(Constants.SELL) || transaction.getTradeType().equalsIgnoreCase(Constants.USAGE))) {
                List<Transaction.TransactionItemDetails> itemDetails = concatItemDetails(existingTransaction, transaction);
                updateQuantity(itemDetails, itemsList, transaction.getTradeType());
            }
            else if ((existingTransaction.getTradeType().equalsIgnoreCase(Constants.SELL) || existingTransaction.getTradeType().equalsIgnoreCase(Constants.USAGE)) && transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
                List<Transaction.TransactionItemDetails> itemDetails = concatItemDetails(existingTransaction, transaction);
                updateQuantity(itemDetails, itemsList, transaction.getTradeType());
            }
            else {
                throw new CustomException(Constants.INVALID_TRADE_TYPE);
            }
        }
        else {
            updateQuantity(transaction.getTransactionItemDetails(), itemsList, transaction.getTradeType());
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
        Map<Long, Transaction.TransactionItemDetails> itemDetailsMap = itemDetails.stream()
                .collect(Collectors.toMap(
                        Transaction.TransactionItemDetails::getItemId,
                        details -> details
                ));
        if (tradeType.equalsIgnoreCase(Constants.BUY)) {
            itemsList.forEach(item -> {
                if (itemDetailsIds.contains(item.getId())) {
                    item.setQuantity(item.getQuantity() + itemDetailsIdQuantityMap.get(item.getId()));
//                    item.setPurchasePrice(itemDetailsMap.get(item.getId()).getCost());
                }
            });
        }
        else if (tradeType.equalsIgnoreCase(Constants.SELL) || tradeType.equalsIgnoreCase(Constants.USAGE)) {
            itemsList.forEach(item -> {
                if (itemDetailsIds.contains(item.getId())) {
                    int updatedQuantity = item.getQuantity() - itemDetailsIdQuantityMap.get(item.getId());
                    if (updatedQuantity < 0) {
                        throw new CustomException(String.format(
                                "%s - %s doesn't have required %d quantity. Only have %d.",
                                item.getCategory().getName(),
                                item.getName(),
                                itemDetailsIdQuantityMap.get(item.getId()),
                                item.getQuantity())
                        );
                    }
                    item.setQuantity(updatedQuantity);
                }
            });
        }
        else {
            throw new CustomException(Constants.INVALID_TRADE_TYPE);
        }
    }

    private void removeAndAddQuantity(List<Transaction.TransactionItemDetails> removeTransactionItemDetails, List<Transaction.TransactionItemDetails> addTransactionItemDetails, List<Item> itemsList) {
        updateQuantity(addTransactionItemDetails, itemsList, Constants.BUY);
        updateQuantity(removeTransactionItemDetails, itemsList, Constants.SELL);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();
        Collections.reverse(transactionList);
        return transactionList;
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new CustomException("Transaction not found with id: " + transactionId);
        }
        List<Item> itemsList = itemRepository.findAll();
        Transaction transaction = optionalTransaction.get();
        if (transaction.getTradeType().equalsIgnoreCase(Constants.BUY)) {
            updateQuantity(transaction.getTransactionItemDetails(), itemsList, Constants.SELL);
        }
        else if ((transaction.getTradeType().equalsIgnoreCase(Constants.SELL) || transaction.getTradeType().equalsIgnoreCase(Constants.USAGE))) {
            updateQuantity(transaction.getTransactionItemDetails(), itemsList, Constants.BUY);
        }
        else {
            throw new CustomException(Constants.INVALID_TRADE_TYPE);
        }
        transactionRepository.delete(transaction);
    }
}
