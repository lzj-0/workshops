
public class BankApp {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount("test");
        System.out.println(acc.getAccountNumber());
        System.out.println(acc.getHolderName());
        acc.deposit(1000.0f);
        acc.withdraw(100.0f);
        acc.get_transactions();
        acc.closeAccount();
        acc.getClosingDate();
        //acc.deposit(100.0f);

        FixedDepositAccount acc2 = new FixedDepositAccount("test2", 1000.0f);
        acc2.setInterest(6.5f);
        //acc2.setInterest(1.0f);
        acc2.deposit(500f);
        System.out.println(acc2.getBalance());

    }
}
