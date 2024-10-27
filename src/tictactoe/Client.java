package tictactoe;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        if (args.length < 1) {
            System.out.println("Input <hostname>:<port>");
            System.exit(-1);
        }

        String[] input = args[0].split(":");
        String host = input[0];
        Integer port = Integer.parseInt(input[1]);

        Socket socket = new Socket(host, port);
        Console cons = System.console();

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        Boolean started = false;
        String outcome;
        Boolean again;
        Boolean exit = false;
        String turn = null;

        while (true) {
            if (!started) {
                System.out.println("Game of Tic-Tac-Toe");
                String side = cons.readLine("Pick a side (X/O): ");
                if (!"XxOo".contains(side)) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }
                dos.writeUTF(side);
                dos.flush();
                started = true;
                System.out.println(dis.readUTF());  // print player and computer symbol
                System.out.println(dis.readUTF());
                System.out.println(dis.readUTF());  // print default board
            }

            while (started) {
                String position = cons.readLine("Enter position: ");
                if (!"123456789".contains(position) || position.equals("")) {
                    System.out.println("Invalid position value, please try again");
                    continue;
                }
                dos.writeUTF(position);
                dos.flush();
                Boolean result = dis.readBoolean();
                System.out.println(result);
                if (!result) {
                    System.out.println("Error. Position already taken, try other positions");
                    continue;
                }
                System.out.println("Computer's turn");
                System.out.println(dis.readUTF()); // print player board
                outcome = dis.readUTF();    // outcome
                if (!outcome.equals("Playing")) {
                    System.out.println(outcome);
                    again = cons.readLine("Play again? (y/n): ").equals("y") ? true : false;
                    dos.writeBoolean(again);
                    dos.flush();
                    if (again) {
                        started = false;
                        break;
                    } else {
                        exit = true;
                        break;
                    }
                }

                System.out.println("Waiting for computer");

                System.out.println(dis.readUTF());
                System.out.println(dis.readUTF()); // print computer board
                outcome = dis.readUTF();    // outcome
                if (!outcome.equals("Playing")) {
                    System.out.println(outcome);
                    again = cons.readLine("Play again? (y/n): ").equals("y") ? true : false;
                    dos.writeBoolean(again);
                    if (again) {
                        started = false;
                        break;
                    } else {
                        exit = true;
                        break;
                    }
                }
            }
            if (exit) {
                break;
            }
        }
    }
}
