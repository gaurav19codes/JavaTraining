package Day1;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BankSystem {

    public static void main(String[] args)
    {

        List<Account> accounts = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
        {
            accounts.add(new Account("User- " + i, i, 100));
        }

        for (int i = 0; i < 5; i++)
        {
            if (i != 4)
            {
                accounts.get(i).setTransTarget(accounts.get(i + 1));
            } else
            {
                accounts.get(i).setTransTarget(accounts.get(0));
            }

            accounts.get(i).start();
        }

        try
        {
            for (int i = 0; i < 5; i++)
            {
                accounts.get(i).join();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        accounts.forEach(n -> System.out.println(n.getBalance()));
    }

    public static void transactMoney(Account sender, Account receiver, float amountToTransfer)
    {
        Account firstLock = sender;
        Account secondLock = receiver;

        if (sender.accountNumber < receiver.accountNumber)
        {
            firstLock = receiver;
            secondLock = sender;
        }

        System.out.println(Thread.currentThread().getName() + " waiting for first lock on user " + firstLock.accountNumber);
        synchronized (firstLock)
        {
            System.out.println(Thread.currentThread().getName() + " got first lock on user " + firstLock.accountNumber);
            System.out.println(Thread.currentThread().getName() + " waiting for second lock on user " + secondLock.accountNumber);
            synchronized (secondLock)
            {
                System.out.println(Thread.currentThread().getName() + " got second lock on user " + secondLock.accountNumber);
                sender.subtractBalance(amountToTransfer);

                int delayTime = ThreadLocalRandom.current().nextInt(100, 600);

                try
                {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                receiver.addBalance(amountToTransfer);
                System.out.println("\t" + Thread.currentThread().getName() + " withdraw $" + amountToTransfer);
            }

            System.out.println("\t" + Thread.currentThread().getName() + " released lock on user " + firstLock.accountNumber + " and " + secondLock.accountNumber);
        }
    }
}