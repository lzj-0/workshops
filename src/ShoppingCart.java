import java.util.ArrayList;
import java.io.Console;

public class ShoppingCart {
    public ArrayList<String> arr;
    public ShoppingCart() {
        arr = new ArrayList<String>();
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

    public static void main(String[] args) {
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.menu();
        Console cons = System.console();
        boolean force_exit = false;
        while (true) {
            String input = cons.readLine(">>> ");
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
