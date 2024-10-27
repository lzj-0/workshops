package fc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CookieClientHandler implements Runnable {
    private final Socket socket;
    private String filename;

    public CookieClientHandler(Socket socket, String filename) {
        this.socket = socket;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            while (true) {
                InputStream is = this.socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
        
                OutputStream os = this.socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
    
                Cookie cookie = new Cookie();
                String command = dis.readUTF();

                String name = Thread.currentThread().getName();
    
                switch (command) {
                    case "get-cookie":
                        String output = cookie.getRandomCookie(cookie.read(filename));
                        dos.writeUTF("cookie-text " + output);
                        System.out.printf("[%s] cookie sent to Client\n", name);
                        dos.flush();
                        break;
                    case "close":
                        //exit = true;
                        dos.writeUTF("exit");
                        dos.flush();
                        // dos.close();
                        // os.close();
                        // dis.close();
                        // dos.close();
                        System.out.printf("[%s] Exiting thread\n", name);
                        this.socket.close();
                        //server.close();
                        break;
                }
    
            } 
        } catch (IOException e) {
                //e.printStackTrace();
            }

    }
}
