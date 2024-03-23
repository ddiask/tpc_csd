package csd.client;

import org.apache.log4j.BasicConfigurator;

import java.io.Console;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: demo.map.MapInteractiveClient <client id>");
        }
        BasicConfigurator.configure();
        int clientId = Integer.parseInt(args[0]);
        Client client =new Client(clientId);
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        int transactionID,  timestamp,  destAddr,  amount;
        String comment, values;
        while(!exit) {
            System.out.println("Select an option:");
            System.out.println("0 - Terminate this client");
            System.out.println("1 - Insert value into the map");
            System.out.println("2 - Read value from the map");

            int cmd = scanner.nextInt();
            scanner.nextLine();

            switch (cmd) {
                case 0:
                    client.close();
                    exit = true;
                    break;
                case 1:
                    System.out.println("Putting value in the map");
                    values= scanner.nextLine();
                    timestamp= (int) System.currentTimeMillis();
                    String[] data=values.split(" ");
                    transactionID= Integer.parseInt(data[0]);
                    destAddr= Integer.parseInt(data[1]);
                    amount= Integer.parseInt(data[2]);
                    comment = scanner.nextLine();
                    client.sendTransaction(transactionID, timestamp, destAddr, amount, comment);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Reading value from the map");
                    transactionID= scanner.nextInt();
                    client.getTransaction(transactionID);
                    System.out.println();
                    break;
                default:
                    break;
            }
        }
    }
}
