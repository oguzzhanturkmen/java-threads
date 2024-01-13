package threads;

public class ThreadCreationWays {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());


        //First one
        MyThread myThread = new MyThread();
        myThread.start();

        myThread.setName("MyThread");

        //Second one
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();


        //Anonymous class and a functional interface
        //Functional interface is an interface that has only one abstract method
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Runnable is running");
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread1.start();

        //With lambda
        Thread thread2 = new Thread(() -> {
            System.out.println("Lambda Runnable is running");
            System.out.println(Thread.currentThread().getName());
        });
        thread2.start();


    }
    //There are two ways to create a thread

    //1. By extending Thread class
    static class MyThread extends Thread{
        @Override
        public void run() {
            //super.run();
            // We write the code that we want to run in a separate thread
            System.out.println("MyThread is running");
            System.out.println(Thread.currentThread().getName());
        }
    }

    //2. By implementing Runnable interface
    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("MyRunnable is running");
            System.out.println(Thread.currentThread().getName());
        }
    }
}
