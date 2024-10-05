import java.io.*;
import java.util.*;

public class ShoppingCartDB {
    public ArrayList<String> loadCart(String db, String username) throws IOException {
            FileReader fr = new FileReader(String.format("%s/%s.db", db, username));
            LineNumberReader lnr = new LineNumberReader(fr);
            String item;
            ArrayList<String> cart = new ArrayList<>();

            while (null != (item = lnr.readLine())) {
                cart.add(item);
            }
            if (cart.size() == 0) {
                System.out.printf("%s, your cart is empty\n", username);
            } else {
                System.out.printf("%s, your cart contains the following items\n", username);
                for (int i = 1; i <= cart.size(); i++) {
                    System.out.printf("%d. %s\n", i, cart.get(i - 1));
                }
            }
            lnr.close();
            fr.close();
            return cart;
    }

    public void saveCart(String db, String username, ArrayList<String> items) throws IOException {
        File f = new File(String.format("%s/%s.db", db, username));
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String item: items) {
            bw.write(item + "\n");
        }
        bw.flush();
        bw.close();
        fw.close();
        System.out.println("Your cart has been saved");
    }
}
