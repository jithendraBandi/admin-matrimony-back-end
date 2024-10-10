package projects.blue_whale.service;

import projects.blue_whale.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();
}
