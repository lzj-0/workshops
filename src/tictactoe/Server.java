package tictactoe;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Input <port>");
            System.exit(-1);
        }   

        Integer port = Integer.parseInt(args[0]);
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        System.out.println("Got a connection");

        Boolean again = false;

        while (true) {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            TicTacToe ttt = new TicTacToe();

            String side = dis.readUTF();
            ttt.startGame(side);
            dos.writeUTF("Game started, %s goes first\nPlayer: %s\nComputer: %s"
                            .formatted(ttt.getState(), ttt.getPlayer(), ttt.getComputer()));
            dos.writeUTF("%s's turn:\n".formatted(ttt.getState()));
            dos.writeUTF(ttt.printBoard());
            dos.flush();
            String outcome;
            Boolean written = null;


            while (true) {
                System.out.println("\n");
                if (ttt.getState().equals("computer")) {
                    System.out.println("Computer playing");
                    ttt.computerTurn();
                    written = true;
                    dos.writeUTF("%s's turn:\n".formatted(ttt.getState()));
                    dos.flush();
                    outcome = ttt.checkWin();
                    
                } else {
                    String playerPosition = dis.readUTF();
                    System.out.println("Player playing");
                    written = ttt.writeBoard(Integer.parseInt(playerPosition));
                    dos.writeBoolean(written);
                    dos.flush();
                    outcome = ttt.checkWin();
                }
                if (written) {
                    dos.writeUTF(ttt.printBoard());
                    System.out.println("Sent board to client");
                    dos.writeUTF(outcome);
                    dos.flush();
                    if (!outcome.equals("Playing")) {
                        again = dis.readBoolean();
                        break;
                    }
                }
            }
            if (again) {
                continue;
            } else {
                break;
            }
        }
    }
}
