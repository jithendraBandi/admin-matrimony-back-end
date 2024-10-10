package projects.blue_whale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.blue_whale.dto.ApiResponse;
import projects.blue_whale.entity.Transaction;
import projects.blue_whale.service.TransactionService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveTransaction(@RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(new ApiResponse("Transaction has successfully updated."), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllTransactions() {
        List<Transaction> transactionList = transactionService.getAllTransactions();
        return new ResponseEntity<>(new ApiResponse(transactionList), HttpStatus.OK);
    }
}

