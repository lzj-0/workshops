package fc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String[] input = args[0].split(":");
        String host = input[0];
        Integer port = Integer.parseInt(input[1]);

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
                }
            } catch (Exception e) {
                System.out.println("Closing server connection: " + e.getMessage());
            } finally {
                dis.close();
                is.close();
                dos.close();
                os.close();
                socket.close();
            }

        
        }

        
    }
}
