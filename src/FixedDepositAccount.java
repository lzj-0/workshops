
public class FixedDepositAccount extends BankAccount {
    private Float interest = 3.0f;
    private int duration = 6;
    private boolean change_interest = false;
    private boolean change_duration = false;

    public FixedDepositAccount(String name, Float balance) throws IllegalArgumentException {
        super(name, balance);
    }

    public FixedDepositAccount(String name, Float balance, Float interest) throws IllegalArgumentException {
        super(name, balance);
        this.interest = interest;
    }

    public FixedDepositAccount(String name, float balance, Float interest, Integer duration) throws IllegalArgumentException {
        super(name, balance);
        this.interest= interest;
        this.duration = duration;
    }


    public Float getInterest() {
        return this.interest;
    }

    public void setInterest(Float interest) throws IllegalArgumentException {
        if (!change_interest) {
            this.interest = interest;
            change_interest = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (!change_duration) {
            this.duration = duration;
            change_duration = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    // will work even if you cast FDA into BankAccount as JDK determines the object identity at runtime (runtime polymorphism)
    public void setBalance(Float amount) {
        System.out.println("Set account balance not allowed for fixed deposit account");
    }

    @Override
    public void deposit(Float amount) {
        System.out.println("Deposit not allowed for fixed deposit account");
    }

    @Override
    public void withdraw(Float amount) {
        System.out.println("Withdrawal not allowed for fixed deposit account");
    }

    @Override
    public Float getBalance() {
        // this.getBalance() throws a infinite error loop
        return super.getBalance()*(1 + this.getInterest()/100.0f);
    }
    

}
