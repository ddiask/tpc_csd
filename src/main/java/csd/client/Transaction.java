package csd.client;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int transactionID,  timestamp,  destAddr,  amount;
    private String comment;

    public Transaction(int transactionID, int timestamp, int destAddr, int amount, String comment) {
        this.transactionID = transactionID;
        this.timestamp = timestamp;
        this.destAddr = destAddr;
        this.amount = amount;
        this.comment = comment;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getDestAddr() {
        return destAddr;
    }

    public int getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", timestamp=" + timestamp +
                ", destAddr=" + destAddr +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
