package csd.client;

import bftsmart.tom.ServiceProxy;
import csd.utils.GenericSerializer;

import java.util.logging.Logger;

public class Client implements  TransactionClient{


    Logger logger= Logger.getLogger(Client.class.getName());
    ServiceProxy serviceProxy;


    public Client(int clientId){
        serviceProxy= new ServiceProxy(clientId);
    }
    @Override
    public void sendTransaction(int transactionID, int timestamp, int destAddr, int amount, String comment) {
        byte[] returnData=null;
        try {
            Transaction transaction = new Transaction(transactionID, timestamp, destAddr, amount, comment);
            byte[] data = GenericSerializer.serialize(transaction);
            returnData= serviceProxy.invokeOrdered(data);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

        if(returnData!= null){
           Transaction transaction = GenericSerializer.deserialize(returnData);
           logger.info(transaction.toString());
        }else{
            logger.info("OLLALLLLLLALALALALLALALALALALALA");
        }
    }

    @Override
    public void getTransaction(int transactionID) {
        byte[] returnData=null;
        try {
            byte[] data = GenericSerializer.serialize(transactionID);
            returnData= serviceProxy.invokeUnordered(data);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(returnData!= null){
            Transaction transaction = GenericSerializer.deserialize(returnData);
            System.out.println(transaction.toString());
        }
    }

    public void close() {
        serviceProxy.close();
    }
}
