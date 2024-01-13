package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ThreadCreator threadCreator1 = new ThreadCreator("Task 1", 1000);
        ThreadCreator threadCreator2 = new ThreadCreator("Task 2", 2000);
        ThreadCreator threadCreator3 = new ThreadCreator("Task 3", 3000);
        ThreadCreator threadCreator4 = new ThreadCreator("Task 4", 4000);
        ThreadCreator threadCreator5 = new ThreadCreator("Task 5", 5000);
        ThreadCreator threadCreator6 = new ThreadCreator("Task 6", 6000);
        ThreadCreator threadCreator7 = new ThreadCreator("Task 7", 7000);

        executorService.execute(threadCreator1);
        executorService.execute(threadCreator2);
        executorService.execute(threadCreator3);
        executorService.execute(threadCreator4);
        executorService.execute(threadCreator5);
        executorService.execute(threadCreator6);
        executorService.execute(threadCreator7);

        executorService.shutdown();
    }

}

class ThreadCreator extends Thread {

    public String task;
    public int duration;

    public ThreadCreator(String task, int duration) {
        this.task = task;
        this.duration = duration;
    }


    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running the task " + task);
    }
}
