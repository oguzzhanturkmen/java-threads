package threads;

public class MultiThreading01 {
    public static void main(String[] args) {
        Counter c1 = new Counter("Counter 1");
        Counter c2 = new Counter("Counter 2");
        Counter c3 = new Counter("Counter 3");
        Counter c4 = new Counter("Counter 4");
        Counter c5 = new Counter("Counter 5");
        Counter c6 = new Counter("Counter 6");
        Counter c7 = new Counter("Counter 7");
        Counter c8 = new Counter("Counter 8");
        Counter c9 = new Counter("Counter 9");
        Counter c10 = new Counter("Counter 10");

        CounterWithThread ct1 = new CounterWithThread("Counter 1");
        CounterWithThread ct2 = new CounterWithThread("Counter 2");
        CounterWithThread ct3 = new CounterWithThread("Counter 3");
        CounterWithThread ct4 = new CounterWithThread("Counter 4");
        CounterWithThread ct5 = new CounterWithThread("Counter 5");
        CounterWithThread ct6 = new CounterWithThread("Counter 6");
        CounterWithThread ct7 = new CounterWithThread("Counter 7");
        CounterWithThread ct8 = new CounterWithThread("Counter 8");
        CounterWithThread ct9 = new CounterWithThread("Counter 9");
        CounterWithThread ct10 = new CounterWithThread("Counter 10");

        c1.count();
        c2.count();
        c3.count();
        c4.count();
        c5.count();
        c6.count();
        c7.count();
        c8.count();
        c9.count();
        c10.count();

        ct1.start();
        ct2.start();
        ct3.start();
        ct4.start();
        ct5.start();
        ct6.start();
        ct7.start();
        ct8.start();
        ct9.start();
        ct10.start();



    }


}

class Counter {
    public String name;

    public Counter(String name) {
        this.name = name;
    }

    public void count() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + " " + i);
        }
    }

}

class CounterWithThread extends Thread {
    public String name;

    public CounterWithThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            System.out.println(name + " " + i);
        }
    }
}
