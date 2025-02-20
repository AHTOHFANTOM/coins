package org.example.ecosub;

public class Transaction {
    private String type;
    private int amount;
    private long timestamp;

    public Transaction(String type, int amount, long timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}