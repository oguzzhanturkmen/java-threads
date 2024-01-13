package threads;

import java.util.concurrent.Semaphore;

public class Semaphore01 {
    //Semaphore is a class in java.util.concurrent package
    // introduced in JDK 1.5.
    //It is a counting semaphore which is used to control the number of threads that can access a shared resource.
    //Unlike lock, semaphore is a signaling mechanism.
    //It maintains a set of permits.
    //Each acquire() blocks if necessary until a permit is available, and then takes it.
    //Each release() adds a permit, potentially releasing a blocking acquirer.
    //However, no actual permit objects are used; the Semaphore just keeps a count of the number available and acts accordingly.
    //Semaphores are often used to restrict the number of threads than can access some (physical or logical) resource.
    //For example, here is a class that uses a semaphore to control access to a pool of items:

//The difference betweeen semaphore and synchronized is that semaphore can be used to control the number of threads that can access a resource in a given time.

    //Semaphore example
    //Let's say we have a car wash with 3 washing stations.
    //We have 10 cars to wash.
    //We want to wash 3 cars at a time.
    //We want to wash all the cars in the shortest time possible.
    //We want to print the name of the car and the name of the washing station.

    //We will create a class called Car that extends Thread.
    //It will have a name and a duration.
    //We will create a class called CarWash that has a main method.
    //In the main method, we will create 10 cars.
    //We will create a semaphore with 3 permits.
    //We will create a for loop and start all the cars.
    //We will create a try catch block and acquire the semaphore.
    //We will print the name of the car and the name of the washing station.
    //We will sleep the thread for the duration of the car.
    //We will release the semaphore.
    //We will catch the interrupted exception.
    //We will print the stack trace.

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 10; i++) {
            Car car = new Car("Car " + i, 3000, semaphore);
            car.start();
        }


    }

}
//Car class

class Car extends Thread {
    public String name;
    public int duration;
    Semaphore semaphore;

    public Car(String name, int duration, Semaphore semaphore) {
        this.name = name;
        this.duration = duration;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Car " + name + " is washing at station " + Thread.currentThread().getName());
            Thread.sleep(duration);
            System.out.println("Car " + name + " is done washing at station " + Thread.currentThread().getName());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


