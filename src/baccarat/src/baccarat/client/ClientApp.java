package baccarat.src.baccarat.client;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ClientApp {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Input <hostname>:<port>");
            System.exit(-1);
        }

        String[] input = args[0].split(":");
        String hostname = input[0];
        Integer port = Integer.parseInt(input[1]);

        Socket socket = new Socket(hostname, port);
        Console cons = System.console();

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);


        while (true) {
            String command = cons.readLine("> ");
            if (command.toLowerCase().contains("deal")) {
                dos.writeUTF(command.split(" ")[0].toLowerCase() + "|" + command.split(" ")[1]);
            } else {
                dos.writeUTF(command.replace(" ", "|").toLowerCase());
            }
            String msg = dis.readUTF();
            
            if (msg.contains("B|") && msg.contains("P|")) {
                String[] playerCards = msg.split(",")[0].substring(2).split("\\|");
                String[] bankerCards = msg.split(",")[1].substring(2).split("\\|");
                Integer sumPlayer = Arrays.asList(playerCards).stream().mapToInt(Integer::parseInt).sum();
                Integer sumBanker = Arrays.asList(bankerCards).stream().mapToInt(Integer::parseInt).sum();
                String winner = null;
                Integer points = null;
                if (sumPlayer > sumBanker) {
                    winner = "Player";
                    points = sumPlayer;
                } else if (sumPlayer < sumBanker) {
                    winner = "Banker";
                    points = sumBanker;
                } else {
                    winner = "Draw";
                    points = sumPlayer;
                }
                System.out.println(msg);
                if (winner.equals("Draw")) {
                    System.out.printf("%s with %d points\n", winner, points);
                } else {
                    System.out.printf("%s wins with %d points\n", winner, points);
                }

                File file = new File("src/baccarat/game_history.csv");

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                Boolean firstLine = true;

                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        if (!firstLine) {
                            bw.write("\n");
                        }
                        bw.write(winner.substring(0, 1));
                        bw.flush();
                        break;
                    } else if (line.split(",").length < 6 && line != null) {
                        bw.write("," + winner.substring(0, 1));
                        bw.flush();
                        break;
                    }
                    firstLine = false;
                }

                bw.close();
                fw.close();
                br.close();
                fr.close();

            } else {
                System.out.println(msg);
            }

        }



    }
}
