package fc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String[] input = args[0].split(":");
        String host = input[0];
        Integer port = Integer.parseInt(input[1]);
        boolean exit = false;

        Socket socket = new Socket(host, port);
        Console cons = System.console();

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        while (true) {
            try {
                dos.writeUTF(cons.readLine("> "));
                System.out.println("Sent request to Server");
                String result = dis.readUTF();
                String command = result.split(" ")[0];
    
                switch (command) {
                    case "cookie-text":
                        System.out.println(result.substring(12));
                        break;
                    case "exit":
                        exit = true;
                        dos.close();
                        os.close();
                        dis.close();
                        is.close();
                        socket.close();
                }
                
                if (exit) {
                    System.out.println("Closing client");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Closing server connection: " + e.getMessage());
            }
        }

        
    }
}
