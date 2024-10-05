import java.util.ArrayList;
import java.io.*;

public class ShoppingCart {
    public ArrayList<String> arr;
    private String username;

    public ShoppingCart() {
        arr = new ArrayList<String>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void list() {
        if (arr.isEmpty()) {
            System.out.println("Your cart is empty");
        } else {
            int count = 1;
            for (String item: arr) {
                System.out.println(count + ". " + item);
                count++;
            }
        }
    }

    public void add(String item) {
        if (arr.contains(item)) {
            System.out.printf("You have %s in your cart\n", item);
        } else {
            arr.add(item);
            System.out.println(item + " added to cart");
        }
    }

    public void delete(int ind) {
        if (ind > arr.size() || ind < 1) {
            System.out.println("Incorrect item index");
        } else {
            String item = arr.get(ind - 1);
            arr.remove(ind - 1);
            System.out.println(item + " removed from cart");
        }
    }

    public static void menu() {
        System.out.println("Welcome to your shopping cart");
        System.out.println("=============================");
        System.out.println();
        System.out.println("List items in the cart: type list");
        System.out.println("Add item(s) into the cart: type add xxx, ..., yyy");
        System.out.println("Delete item from the cart: type delete 1");
        System.out.println("Exit/Terminate program: type any other thing");
    }

    public ArrayList<String> login(String db, String username) throws IOException {
        File f = new File(String.format("%s/%s.db", db, username));
        this.setUsername(username);
        if (!f.exists()) {
            // read the file
            boolean result = f.createNewFile();
            if (result) {   
                System.out.println("file created");
            } else {
                System.out.println("file creation failed");
            }
        }
        ShoppingCartDB dbman = new ShoppingCartDB();
        return dbman.loadCart(db, username);
    }

    public void save(String db) throws IOException {
        if (this.getUsername() == null) {
            System.out.println("Saving only allowed for login users. Please log in first.");
        } else {
            ShoppingCartDB dbman = new ShoppingCartDB();
            dbman.saveCart(db, this.getUsername(), this.arr);
        }
    }

    public void users() {
        File dir = new File("cartdb");
        System.out.println("The following users are registered");
        int i = 1;
        for (File f: dir.listFiles()) {
            //System.out.printf("%d. %s", i, f.toString().split(".")[0]);
            System.out.printf("%d. %s\n", i, f.getName().substring(0, f.getName().length() - 3));
            i++;
        }
    }



    public static void main(String[] args) throws IOException {
        String dir;
        if (args.length > 0 && (new File(args[0])).exists()) {
            dir = args[0];
        } else {
            dir = "db";
            File f = new File(dir);
            if (!f.exists()) {
                System.out.println("directory does not exist");
                boolean created = f.mkdir();
                if (created) {
                    System.out.printf("new directory %s created\n", dir);
                }
            }
        }

        ShoppingCart sc = new ShoppingCart();

        ShoppingCart.menu();
        Console cons = System.console();
        boolean force_exit = false;
        while (true) {
            String input = cons.readLine("> ");
            String func = input.split(" ")[0];

            switch (func) {
                case "list":
                    sc.list();
                    break;
                case "add":
                    String[] items = input.replace(",", " ").substring(3).split("\\s+");
                    for (int i = 1; i < items.length; i++) {
                        sc.add(items[i].replace(",", ""));
                    }
                    break;
                case "delete":
                    sc.delete(Integer.parseInt(input.split(" ")[1]));
                    break;
                case "login":
                    String user = input.split(" ")[1];
                    sc.arr = sc.login(user, dir);
                    break;
                case "save":
                    sc.save(dir);
                    break;
                case "users":
                    sc.users();
                    break;
                default:
                    String exit = cons.readLine("Invalid input, do you want to want to terminate program? ");
                    if (exit.toUpperCase().equals("Y") || exit.toUpperCase().equals("YES")) {
                        force_exit = true;
                    }
            }
            if (force_exit) {
                break;
            }

        }

    }
}
