package threads;

public class Volatile01 {
    public  volatile   static  int flag = 0;

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run(){
                while(flag == 0){
                    System.out.println("Thread 1 is running");
                }
                System.out.println("Thread 1 is finished");
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                flag = 1;
                System.out.println("Thread 2 is finished");
            }
        });
        thread2.start();
    }
}
