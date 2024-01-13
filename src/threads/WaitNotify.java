package threads;

public class WaitNotify {
    public static int balance = 0;

    public synchronized void  deposit(int amount) {
        System.out.println(Thread.currentThread().getName());
        balance += amount;
        System.out.println("Deposit: " + amount + " Balance: " + balance);
        notify();

    }

    public synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName());
        if (balance < amount) {
            System.out.println("Insufficient balance");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (balance >= amount){

            balance -= amount;
            System.out.println("Withdraw: " + amount + " Balance: " + balance);
        }
        else {
            System.out.println("Insufficient balance");
        }

    }

    public static void main(String[] args) {
        final  WaitNotify waitNotify = new WaitNotify();
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                waitNotify.withdraw(100);
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                waitNotify.deposit(200);
            }
        });
        thread2.start();
    }
}
