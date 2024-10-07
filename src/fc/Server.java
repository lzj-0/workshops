package fc;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        String file = args[1];
        // System.out.println("Current working directory: " + System.getProperty("user.dir"));


        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        while (true) {
            String command = dis.readUTF();
            Cookie cookie = new Cookie();

            switch (command) {
                case "get-cookie":
                    String output = cookie.getRandomCookie(cookie.read(file));
                    dos.writeUTF("cookie-text " + output);
                    System.out.println("cookie sent to Client");
                    break;
                case "close":
                    dos.close();
                    os.close();
                    dis.close();
                    dos.close();
                    socket.close();
                    server.close();
            }
        }

    }
}
