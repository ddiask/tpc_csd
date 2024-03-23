package csd.client;

public interface TransactionClient {
    void sendTransaction (int transactionID, int timestamp, int destAddr, int amount, String comment);

    void getTransaction (int transactionID);
}
