package baccarat.src.baccarat.server;

import java.io.*;
import java.util.*;
import java.net.*;

public class ServerApp {
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Input <port> <number of decks>");
            System.exit(-1);
        }

        Integer port = Integer.parseInt(args[0]);
        Integer decks = Integer.parseInt(args[1]);

        ServerSocket server = new ServerSocket(port);

        BaccaratEngine be = new BaccaratEngine();
        be.initDecks(decks);
        Integer accBal = -1;
        String user = null;
        Integer betsPlaced = null;


        while (true) {
            Socket socket = server.accept();    // this line blocks and wait for new connection from a new client
            System.out.println("Got a connection");

            while (true) {
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
    
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
    
                String command = dis.readUTF();
                String[] commandArray = command.split("\\|");
                System.out.println("Received from client: " + command);
    
                switch (commandArray[0]) {
                    case "login":
                        if (commandArray.length == 3) {
                            String name = commandArray[1];
                            Integer funds = Integer.parseInt(commandArray[2]);
                            System.out.println("Logging in");
                            accBal = be.login(name, funds);
                            user = name;
                            dos.writeUTF("Login successful");
                        } else {
                            dos.writeUTF("Error! Input: Login <name> <funds>");
                        }
                        break;
                    case "bet":
                        if (user == null) {
                            dos.writeUTF("Please login first");
                            continue;
                        } else if (commandArray.length == 2) {
                            Integer bet = Integer.parseInt(commandArray[1]);
                            betsPlaced = be.bet(accBal, bet);
                            if (betsPlaced == -1) {
                                dos.writeUTF("Insufficient Amount");
                            } else {
                                dos.writeUTF("Placed a bet of " + betsPlaced);
                            }
                        } else {
                            dos.writeUTF("Error! Input: Bet <bet>");                        
                        }
                        break;
                    case "deal":
                        if (user == null) {
                            dos.writeUTF("Please login first");
                            continue;
                        } else if (commandArray.length == 2 && (commandArray[1].equals("B") || commandArray[1].equals("P"))) {
                            if (betsPlaced == null) {
                                dos.writeUTF("Please place a bet first");
                            } else if (betsPlaced == -1) {
                                dos.writeUTF("Insufficient amount, add more money");
                            } else {
                                String side = commandArray[1];
                                dos.writeUTF(be.deal(user, side, betsPlaced));
                                betsPlaced = null;
                            }
                        } else {
                            dos.writeUTF("Error! Input: Deal <B/P>");
                        }
                        break;
                }
            }

        }

    }
}
