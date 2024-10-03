import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BankAccount {
    private final String name;
    private final String accountNumber;
    private Float balance;
    private ArrayList<String> transactions;
    private boolean closed;
    private LocalDate creatingDate;
    private LocalDate closingDate;


    public BankAccount(String name) {
        this.name = name;
        this.accountNumber = Integer.toString((int)(Math.random()*100000000));
        this.balance = 0.0f;
        this.creatingDate = LocalDate.now();
        this.closed = false;
        this.transactions = new ArrayList<String>();
    }

    public BankAccount(String name, float balance) throws IllegalArgumentException {
        this.name = name;
        this.accountNumber = Integer.toString((int)(Math.random()*100000000));
        if (balance < 0) {
            throw new IllegalArgumentException();
        } else {
            this.balance = balance;
        }
        this.creatingDate = LocalDate.now();
        this.closed = false;
        this.transactions = new ArrayList<String>();
    }

    public String getHolderName() {
        return this.name;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Float getBalance() {
        return this.balance;
    }

    public void setBalance(Float amount) {
        this.balance = amount;
    }

    public boolean getClosedStatus() {
        return this.closed;
    }

    public LocalDate getCreatingDate() {
        return this.creatingDate;
    }

    public LocalDate getClosingDate() {
        return this.closingDate;
    }

    public void closeAccount() {
        this.closed = true;
        this.closingDate = LocalDate.now();
        System.out.println("Closing account...");
    }

    public void get_transactions() {
        int i = 1;
        for (String tx: this.transactions) {
            System.out.printf("%d. %s\n", i, tx);
            i++;
        }
    }

    public void deposit(Float amount) throws IllegalArgumentException {
        if (amount < 0 || this.closed) {
            throw new IllegalArgumentException();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:m:s");
            this.setBalance(this.getBalance() + amount);
            String tx = String.format("deposit $%.2f at " + LocalDateTime.now().format(formatter), amount);
            transactions.add(tx);
            System.out.println(tx);
        }
    }

    public void withdraw(Float amount) throws IllegalArgumentException {
        if (amount < 0 || amount > this.balance || this.closed) {
            throw new IllegalArgumentException();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:m:s");
            this.setBalance(this.getBalance() - amount);
            String tx = String.format("withdraw $%.2f at " + LocalDateTime.now().format(formatter), amount);
            transactions.add(tx);
            System.out.println(tx);
        }
    }
}
