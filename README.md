In Java, a thread is a lightweight subprocess, the smallest unit of processing. It's a separate path of execution because each thread runs in a separate stack frame. Multithreading is a Java feature that allows concurrent execution of two or more parts of a program for maximum utilization of CPU.

### **Basics of Java Threads:**

1. **Thread Creation**: There are two ways to create a thread in Java:
    - **Extending the Thread class**: You create a class that extends the **`java.lang.Thread`** class. This class overrides the **`run()`** method available in the Thread class. A thread begins its life inside **`run()`** method.
    - **Implementing the Runnable Interface**: You create a new class that implements **`java.lang.Runnable`** interface and override **`run()`** method. Then you allocate a new Thread object, passing the Runnable object as an argument to the Thread constructor. The new thread begins its life inside **`run()`** method.
2. **Thread States**: A thread can be in one of the following states:
    - **New**: When a thread is created but not started yet.
    - **Runnable**: A thread that is ready to run is moved to runnable state.
    - **Running**: The processor is executing the thread’s code.
    - **Blocked/Waiting**: The thread is alive but currently not eligible to run.
    - **Terminated**: A thread exits the running state.
3. **Thread Priority**: Every Java thread has a priority that helps the operating system determine the order in which threads are scheduled. Java thread priorities range from 1 (the lowest priority) to 10 (the highest priority).

### **Thread Methods:**

Some important methods in the Thread class are:

- **`start()`**: Starts the thread by calling the **`run()`** method.
- **`run()`**: If the thread was constructed using a separate **`Runnable`** target, the **`run()`** method is invoked on that **`Runnable`**.
- **`sleep(long millis)`**: Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
- **`join()`**: Waits for this thread to die.
- **`interrupt()`**: Interrupts this thread.

### **Example:**

Here's a simple example of creating two threads:

```java

class MyThread extends Thread {
    public void run() {
        for(int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getId() + " Value: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Example {
    public static void main(String args[]) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
    }
}

```

In this example, **`MyThread`** extends **`Thread`** class and overrides the **`run()`** method. The **`main`** method creates two threads and starts them. Each thread will execute its **`run`** method.

### **Synchronization:**

When multiple threads need to access a shared resource, you need to ensure that the resource is used by only one thread at a time. This is achieved using synchronization. In Java, the **`synchronized`** keyword can be used to mark methods or blocks of code that should be synchronized.

### **Example of Synchronization:**

```java

class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class MyThread extends Thread {
    Counter counter;

    MyThread(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for(int i = 0; i < 10000; i++) {
            counter.increment();
        }
    }
}

public class Example {
    public static void main(String args[]) {
        Counter c = new Counter();
        MyThread t1 = new MyThread(c);
        MyThread t2 = new MyThread(c);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count: " + c.getCount());
    }
}

```

In this example, multiple threads are incrementing a shared counter. The **`increment`** method is synchronized to ensure that only one thread can execute it at a time. This prevents race conditions on the **`count`** variable.

### **Conclusion:**

Threads are crucial for multi-threaded applications in Java, enabling efficient use of CPU resources. Proper handling of threads, especially in scenarios involving shared resources, is vital to avoid issues like deadlock, race conditions, and thread interference. Java provides a rich API to manage and synchronize threads effectively.

---

### **`join()` Method in Java**

The **`join()`** method of a thread ensures that the thread on which it is called completes its execution before the thread that called the **`join()`** method can continue executing. When a thread calls the **`join()`** method on another thread (say **`T`**), the calling thread goes into a waiting state. It remains in this state until the referenced thread **`T`** completes its task or until the specified timeout passes.

### Example of **`join()`** Method

```java

class ThreadJoinExample extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                // pause for 1 second
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        ThreadJoinExample t1 = new ThreadJoinExample();
        ThreadJoinExample t2 = new ThreadJoinExample();

        // starting first thread
        t1.start();

        try {
            // waiting for thread t1 to finish
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // starting second thread after first thread t1 has finished
        t2.start();
    }
}

```

In this example, **`t1.join()`** causes the main thread to wait until **`t1`** finishes. **`t2`** starts only after **`t1`** has completed its execution.

### **`interrupt()` Method in Java**

The **`interrupt()`** method is used to interrupt a thread. If a thread is in a sleeping or waiting state (i.e., **`sleep()`**, **`wait()`**, **`join()`** methods), calling the **`interrupt()`** method on the thread breaks out the sleeping or waiting state, throwing **`InterruptedException`**. If the thread is not in the sleeping or waiting state, calling the **`interrupt()`** method sets the interrupted status of the thread. This can be later checked using **`Thread.interrupted()`** or **`Thread.isInterrupted()`** methods.

### Example of **`interrupt()`** Method

```java

class ThreadInterruptExample extends Thread {
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(i);
                // sleeping for 1 second
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        ThreadInterruptExample t1 = new ThreadInterruptExample();
        t1.start();
        try {
            // pause for 2 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        // interrupting the thread
        t1.interrupt();
    }
}

```

In this example, **`t1`** starts and begins executing. After the main thread sleeps for 2 seconds, it interrupts **`t1`** using **`t1.interrupt()`**. This causes an **`InterruptedException`** to be thrown in **`t1`**, which is caught and handled in the **`run()`** method.

These examples demonstrate how **`join()`** and **`interrupt()`** methods are used in Java to manage the flow of thread execution. **`join()`** is used to ensure one thread completes before another continues, while **`interrupt()`** is useful to stop a thread's execution or to exit from a blocking state like sleep or wait.

### **Understanding `synchronized` in Java**

The **`synchronized`** keyword in Java is a critical tool for concurrency control, ensuring that only one thread can execute a particular section of code at a time. It's used to prevent thread interference and consistency issues caused by multiple threads accessing shared resources.

1. **Mutual Exclusion**: The primary purpose of the **`synchronized`** keyword is to enforce mutual exclusion. This means that if a **`synchronized`** method or block is being executed by one thread, no other thread can enter any other **`synchronized`** method or block on the same object.
2. **Memory Visibility**: Besides mutual exclusion, **`synchronized`** also ensures that changes made by one thread to shared data are visible to other threads. It introduces a memory barrier, preventing memory caching issues in multithreading.

   ### **How to Use `synchronized`**

   There are two main ways to use the **`synchronized`** keyword:

    1. **Synchronized Method**:
        - You can declare an entire method as **`synchronized`**.
        - Syntax: **`public synchronized void methodName() { /* method body */ }`**
        - When a thread invokes a synchronized method, it automatically acquires the intrinsic lock (or monitor lock) for that method's object.
        1. **Synchronized Block**:
            - Instead of synchronizing the entire method, you can synchronize a block of code within the method.
            - Syntax:

                ```java
                
                synchronized(this or objectReference) {
                    // block of code
                }
                
                ```

            - This approach provides a more granular level of synchronization and can improve performance when you only need to make part of a method thread-safe.
            - The object used for locking (**`this`** or **`objectReference`**) is known as the monitor object.

           ### **Example of Synchronized Method**

            ```java
            
            class Counter {
                private int count = 0;
            
                // Synchronized method
                public synchronized void increment() {
                    count++;
                }
            
                public int getCount() {
                    return count;
                }
            }
            
            class MyThread extends Thread {
                Counter counter;
            
                MyThread(Counter counter) {
                    this.counter = counter;
                }
            
                public void run() {
                    for(int i = 0; i < 10000; i++) {
                        counter.increment();
                    }
                }
            }
            
            public class Example {
                public static void main(String args[]) {
                    Counter c = new Counter();
                    MyThread t1 = new MyThread(c);
                    MyThread t2 = new MyThread(c);
            
                    t1.start();
                    t2.start();
            
                    // Wait for threads to finish
                    try {
                        t1.join();
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            
                    System.out.println("Count: " + c.getCount());
                }
            }
            
            ```

           In this example, the **`increment`** method is synchronized. This ensures that when a thread is executing **`increment`**, no other thread can execute **`increment`** or any other synchronized method on the same **`Counter`** object.

           ### **Points to Note**

            - It's crucial to understand the scope and implications of the lock and use **`synchronized`** judiciously to avoid performance bottlenecks and deadlocks.
            - Deadlocks can occur if multiple threads hold locks on different objects and wait for locks on objects held by the other thread.
            - Overuse of **`synchronized`** can lead to contention and poor performance, especially in high-load scenarios.
        - Other threads attempting to call any synchronized method on the same object will be blocked until the first thread exits the synchronized method, releasing the lock.

### **`volatile` Keyword**

The **`volatile`** keyword in Java is used to mark a Java variable as being stored in main memory. It is a special modifier that ensures the visibility of changes to variables across threads. When a field is declared as **`volatile`**, the Java Memory Model ensures that all reads and writes go straight to the main memory, and thus, changes made in one thread are immediately visible to other threads.

1. **Visibility Guarantee**: Changes made by one thread to a **`volatile`** variable are always visible to other threads immediately. Without **`volatile`**, variables that are shared between threads might not update their values in the main memory instantly due to caching, optimization, etc.
2. **No Atomicity Guarantee**: While **`volatile`** ensures visibility, it does not guarantee atomicity. For example, **`volatile`** is not a substitute for synchronization in every case, especially when the operation involves more than one step (like incrementing a value).
3. **Prevents Reordering**: In addition to ensuring visibility, the **`volatile`** keyword also prevents memory writes and reads from being reordered, which is crucial for ensuring predictable behavior in concurrent programming.

### **When to Use `volatile`**

- **Simple Flag**: It is most commonly used for a flag or signal variable which indicates that an important event has occurred (like a shutdown request).
- **Single Writer Principle**: It works best in situations where the variable is only written to by one thread and read by others.
- **Performance Considerations**: Using **`volatile`** can be a lighter alternative to synchronization when used correctly, as it does not introduce locking overhead.

### **Example of `volatile` Usage**

```java

class SharedObject {
    // Declaring a volatile Java variable
    volatile boolean shutdownRequested;

    public void shutdown() {
        shutdownRequested = true;
    }

    public void doWork() {
        while (!shutdownRequested) {
            // Do some work
        }
    }
}

public class VolatileExample {
    public static void main(String[] args) {
        SharedObject sharedObject = new SharedObject();

        // Thread for doing work
        Thread workThread = new Thread(new Runnable() {
            public void run() {
                sharedObject.doWork();
            }
        });

        // Thread for shutdown
        Thread shutdownThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000); // Sleep for 2 seconds
                    sharedObject.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        workThread.start();
        shutdownThread.start();
    }
}

```

In this example, **`shutdownRequested`** is a **`volatile`** variable. If one thread calls **`shutdown()`**, changing **`shutdownRequested`** to **`true`**, the other thread in **`doWork()`** will see this change almost immediately and exit the loop.

### **Conclusion**

The **`volatile`** keyword is a simple yet effective way to ensure that changes made by one thread are immediately visible to other threads. However, it's important to remember that it only provides visibility guarantees, not atomicity. For more complex operations involving multiple steps or multiple variables, synchronization or other concurrent utilities are required.

### **`Semaphore`** Class

Semaphore in Java is a concurrency tool introduced in the **`java.util.concurrent`** package in Java 5. It's used for controlling access to a shared resource by multiple threads. A semaphore maintains a set of permits, and threads can acquire one or more permits to access the shared resource. If a required permit is not available, the thread will block until the permit is released by another thread.

Here's a detailed explanation with an example:

### **Understanding Semaphore**

1. **Initialization**: Semaphore can be initialized with a specific number of permits. This number represents the allowed number of threads to access a particular resource at a time.
2. **Acquire() Method**: When a thread wants to access the resource, it calls the **`acquire()`** method of the semaphore. If a permit is available, the semaphore assigns it to this thread, and the thread can use the resource. If no permit is available, the thread will block until a permit is released.
3. **Release() Method**: After a thread has finished using the resource, it should call the **`release()`** method to release its permit, making it available for other threads.
4. **Fairness**: Optionally, a Semaphore can be fair, meaning permits are granted in a First-In-First-Out (FIFO) order.

### **Example: A Shared Printer Resource**

Imagine a scenario where you have a printer and multiple threads (representing employees) trying to use it. The printer can only serve one employee at a time. Here's how you can use a semaphore to manage this:

```java

import java.util.concurrent.Semaphore;

public class Printer {

    // Semaphore with 1 permit, representing 1 printer
    private Semaphore semaphore = new Semaphore(1);

    public void printDocument(String document) {
        try {
            semaphore.acquire(); // Acquiring the permit
            System.out.println(Thread.currentThread().getName() + " is printing: " + document);
            Thread.sleep(1000); // Simulating printing delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // Releasing the permit
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();

        // Creating multiple threads to simulate multiple employees
        Thread t1 = new Thread(() -> printer.printDocument("Document 1"));
        Thread t2 = new Thread(() -> printer.printDocument("Document 2"));
        Thread t3 = new Thread(() -> printer.printDocument("Document 3"));

        t1.start();
        t2.start();
        t3.start();
    }
}

```

In this example:

- We create a **`Semaphore`** with only one permit, as the printer can handle only one print job at a time.
- The **`printDocument`** method uses **`semaphore.acquire()`** to get access to the printer. If the printer is being used by another thread, the current thread will wait.
- Once the printing is done, **`semaphore.release()`** is called to release the permit.
- Multiple threads are created to simulate multiple print jobs.

By using a semaphore, you ensure that only one thread can access the printer at any given time, thus avoiding conflicts and resource contention issues.

### **`CountDownLatch` Class**

**`CountDownLatch`** in Java is a synchronization aid that allows one or more threads to wait until a set of operations being performed by other threads completes. It's part of the **`java.util.concurrent`** package introduced in Java 5. A **`CountDownLatch`** is initialized with a given count. This count is decremented by calls to the **`countDown()`** method. Threads waiting for this count to reach zero can call the **`await()`** method on the latch, and are blocked until the count reaches zero.

### **Key Concepts:**

1. **Initialization**: The **`CountDownLatch`** is initialized with a specific count. This count represents the number of events or operations that must occur before the latch is released.
2. **CountDown Method**: Each time a particular event or operation occurs (for which the latch is waiting), the **`countDown()`** method is called. This decreases the count by one.
3. **Await Method**: A thread that needs to wait for the events to complete calls the **`await()`** method. This thread will block until the count reaches zero.
4. **One-Time Use**: A **`CountDownLatch`** is a single-use barrier. Once the count reaches zero, it cannot be reset.

### **Example: Parallel Computation**

Let's consider an example where we have a main task that should only execute after completing certain prerequisite tasks.

```java

import java.util.concurrent.CountDownLatch;

public class MainTaskRunner {

    private static final int NUM_OF_PRE_TASKS = 3;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(NUM_OF_PRE_TASKS);

        // Creating and starting threads for prerequisite tasks
        for (int i = 1; i <= NUM_OF_PRE_TASKS; i++) {
            new Thread(new PreTask(latch, "Task " + i)).start();
        }

        // Main thread waiting for the completion of all pre-tasks
        latch.await();
        System.out.println("All pre-tasks are completed. Executing main task.");
    }

    static class PreTask implements Runnable {
        private final CountDownLatch latch;
        private final String taskName;

        PreTask(CountDownLatch latch, String taskName) {
            this.latch = latch;
            this.taskName = taskName;
        }

        public void run() {
            try {
                System.out.println(taskName + " is being executed");
                Thread.sleep(1000); // Simulating task execution time
                latch.countDown(); // Decrementing the count on latch
                System.out.println(taskName + " is completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

```

In this example:

- A **`CountDownLatch`** is created with a count of **`NUM_OF_PRE_TASKS`** (3 in this case).
- We create and start three **`PreTask`** threads. Each **`PreTask`** represents a prerequisite operation.
- Each **`PreTask`** does some work (simulated by **`Thread.sleep`**) and then calls **`latch.countDown()`**.
- The main thread calls **`latch.await()`**, causing it to block until all three tasks have completed.
- Once all prerequisite tasks have finished (i.e., the count reaches zero), the main task is executed.

This pattern is useful in scenarios where the execution of a certain part of the program should not proceed until specific conditions or operations have been completed by other threads.
