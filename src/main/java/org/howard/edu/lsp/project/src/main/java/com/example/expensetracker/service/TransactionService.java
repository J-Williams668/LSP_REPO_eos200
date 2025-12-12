package com.example.expensetracker.service;

import com.example.expensetracker.domain.TransactionEntity;
import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.TransactionRequest;
import com.example.expensetracker.dto.TransactionResponse;
import com.example.expensetracker.repository.TransactionRepository;
import com.example.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse createTransaction(Long userId, TransactionRequest request) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setUser(user);
        transaction.setType(request.type());
        transaction.setAmount(request.amount());
        transaction.setCategory(request.category());
        transaction.setDescription(request.description());
        transaction.setDateTime(request.dateTime());

        TransactionEntity savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponse(
                savedTransaction.getId(),
                savedTransaction.getType(),
                savedTransaction.getAmount(),
                savedTransaction.getCategory(),
                savedTransaction.getDescription(),
                savedTransaction.getDateTime()
        );
    }

    public List<TransactionResponse> listTransactions(Long userId, LocalDateTime start, LocalDateTime end){
        return transactionRepository.findByUserIdAndDateTime(userId, start, end)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getType(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getDescription(),
                        t.getDateTime()
                ))
                .toList();
    }
}
