package task2;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        Integer port = 3000;

        if (args.length > 0) {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            } else if (args.length == 2) {
                host = args[0];
                port = Integer.parseInt(args[1]);
            } else {
                System.out.println("Wrong input parameters given. Only accept maximum of 2 parameters.");
                System.exit(-1);
            }
        }

        Socket socket = new Socket(host, port);
        System.out.printf("Connected to server on %s:%d\n", host, port);

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        ArrayList<Product> products = new ArrayList<>();

        String request_id = br.readLine().split(" ")[1];
        Integer item_count = Integer.parseInt(br.readLine().split(" ")[1]);
        //System.out.println(item_count);
        Float budget = Float.parseFloat(br.readLine().split(" ")[1]);
        br.readLine();
        Integer count = 0;
        while (count < item_count) {
            if (br.readLine().equals("prod_start")) {
                Integer prod_id = Integer.parseInt(br.readLine().split("\\s+")[1]);
                String title = br.readLine().split("\\s+")[1];
                Float price = Float.parseFloat(br.readLine().split("\\s+")[1]);
                Float rating = Float.parseFloat(br.readLine().split("\\s+")[1]);
                if (br.readLine().equals("prod_end")) {
                    products.add(new Product(prod_id, title, price, rating));
                    count++;
                }
            }
        }
        //System.out.println(products.size());
        List<Product> products_sorted = products.stream().sorted(Comparator.comparing(Product::getRating)
                                    .thenComparing(Product::getPrice).reversed()).collect(Collectors.toList());
        //System.out.println(products_sorted);
        ArrayList<Product> cart = new ArrayList<>();
        Float remainingBudget = budget;
        for (Product prod: products_sorted) {
            if (prod.getPrice() > remainingBudget) {
                continue;
            } else {
                cart.add(prod);
                remainingBudget -= prod.getPrice();
            }
        }
        bw.write("request_id: " + request_id + "\n");
        bw.write("name: Jay\n");
        bw.write("email: jay@gmail.com\n");
        StringBuilder sb = new StringBuilder();
        for (Product prod: cart) {
            sb.append(prod.getProd_id());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        bw.write("items: " + sb.toString() + "\n");
        bw.write("spent: " + (budget - remainingBudget) + "\n");
        bw.write("remaining: " + remainingBudget + "\n");
        bw.write("client_end\n");
        // System.out.println("request_id:  " + request_id + "\n");
        // System.out.println("name:  Jay\n");
        // System.out.println("email:  jay@gmail.com\n");
        // System.out.println("items:  " + sb.toString() + "\n");
        // System.out.println("spent:  " + (budget - remainingBudget) + "\n");
        // System.out.println("remaining:  " + remainingBudget + "\n");
        // System.out.println("client_end\n");
        // System.out.println("here");
        bw.flush();
        System.out.println(br.readLine());
        bw.close();
        osw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
    }
}
