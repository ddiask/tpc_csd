package csd.server;

import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.client.Transaction;
import csd.utils.GenericSerializer;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server extends DefaultSingleRecoverable {

    private Map<Integer, Transaction> replicaMap;

    private Logger logger;

    public Server(int id){
        replicaMap= new TreeMap<>();
        logger= Logger.getLogger(Server.class.getName());
        new ServiceReplica(id, this, this);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: demo.map.MapServer <server id>");
            System.exit(-1);
        }
        BasicConfigurator.configure();
        new Server(Integer.parseInt(args[0]));
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
             ObjectInput objIn = new ObjectInputStream(byteIn)) {
            replicaMap = (Map<Integer, Transaction>)objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error while installing snapshot", e);
        }
    }

    @Override
    public byte[] getSnapshot() {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(replicaMap);
            return byteOut.toByteArray();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while taking snapshot", e);
        }
        return new byte[0];
    }

    @Override
    public byte[] appExecuteOrdered(byte[] bytes, MessageContext messageContext) {
        logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Transaction request= GenericSerializer.deserialize(bytes);
        logger.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        replicaMap.put(request.getTransactionID(),request);
        logger.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        return GenericSerializer.serialize(request);
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext messageContext) {
        Integer transactionId= GenericSerializer.deserialize(bytes);
        Transaction response= replicaMap.get(transactionId);
        return GenericSerializer.serialize(response);
    }
}
