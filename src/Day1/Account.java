package Day1;

public class Account extends Thread
{
    volatile float balance;
    Account target;

    int accountNumber;

    public Account(String name, int accountNumber, int balance)
    {
        super(name);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @Override
    public void run()
    {

        for (int i = 0; i < 2; i++)
        {
            BankSystem.transactMoney(this, target, 100);
        }
    }

    public void setTransTarget(Account target)
    {
        this.target = target;
    }

    private void setBalance(float balance)
    {
        this.balance = balance;
    }

    public float getBalance()
    {
        return balance;
    }

    public void addBalance(float balance)
    {
        setBalance(getBalance() + balance);
    }

    public void subtractBalance(float balance)
    {
        setBalance(getBalance() - balance);
    }
}