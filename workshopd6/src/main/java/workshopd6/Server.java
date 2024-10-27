package fc;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// CMD
// cd src
// jar -c -v -f fortunecookie.jar -e fc.Server fc/*.class cookie_file.txt
// jar tf fortunecookie.jar (check for contents inside jar file)
// java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
// java -cp fortunecookie.jar fc.Client localhost:12345


public class Server {
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        String file = args[1];
        // System.out.println("Current working directory: " + System.getProperty("user.dir"));
        // boolean exit = false;

        ServerSocket server = new ServerSocket(port);
        ExecutorService thrPool = Executors.newFixedThreadPool(2);


        int connections = 0;

        while (true) {
            String name = Thread.currentThread().getName();
            System.out.printf("[%s] %d Waiting for connection\n", name, connections);
            connections++;

            Socket socket = server.accept();
            System.out.printf("[%s] Got a client connection\n", name);

            CookieClientHandler worker = new CookieClientHandler(socket, file);

            thrPool.submit(worker);

            System.out.printf("[%s] Submitted connection handler to thread pool\n", name);

            // if (exit) {
            //     System.out.println("Closing server");
            //     break;
            // }
        }

    }
}
