package org.example.ecosub;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private String username;
    private int balance;
    private List<Transaction> transactions;

    public PlayerData(String username) {
        this.username = username;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        removeOldTransactions();
    }

    private void removeOldTransactions() {
        long fourDaysAgo = System.currentTimeMillis() - (4 * 24 * 60 * 60 * 1000);
        transactions.removeIf(transaction -> transaction.getTimestamp() < fourDaysAgo);
    }
}