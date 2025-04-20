import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();
        new Thread(deadlock::operation1, "T1").start();
        new Thread(deadlock::operation2, "T2").start();
    }

    public void operation1() {
        lock1.lock();
        System.out.println(Thread.currentThread().getName() + ": lock1 acquired, waiting to acquire lock2.");
        sleep(50);

        lock2.lock();
        System.out.println(Thread.currentThread().getName() + ": lock2 acquired");

        System.out.println(Thread.currentThread().getName() + ": executing first operation.");

        lock2.unlock();
        lock1.unlock();
    }

    public void operation2() {
        lock2.lock();
        System.out.println(Thread.currentThread().getName() + ": lock2 acquired, waiting to acquire lock1.");
        sleep(50);

        lock1.lock();
        System.out.println(Thread.currentThread().getName() + ": lock1 acquired");

        System.out.println(Thread.currentThread().getName() + ": executing second operation.");

        lock1.unlock();
        lock2.unlock();
    }

    // helper methods
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
