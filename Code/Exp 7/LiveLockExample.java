import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLockExample {

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        LiveLockExample livelock = new LiveLockExample();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }

    public void operation1() {
        while (true) {
            tryLock(lock1, 10000); // Increase timeout to 10 seconds
            System.out.println(Thread.currentThread().getName() + ": lock1 acquired, trying to acquire lock2.");
            sleep(50);

            if (tryLock(lock2, 10000)) { // Increase timeout to 10 seconds
                System.out.println(Thread.currentThread().getName() + ": lock2 acquired.");
                break;
            } else {
                System.out.println(Thread.currentThread().getName() + ": cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                randomDelay();
            }
        }
        System.out.println(Thread.currentThread().getName() + ": executing first operation.");
        lock2.unlock();
        lock1.unlock();
    }

    public void operation2() {
        while (true) {
            tryLock(lock2, 10000); // Increase timeout to 10 seconds
            System.out.println(Thread.currentThread().getName() + ": lock2 acquired, trying to acquire lock1.");
            sleep(50);

            if (tryLock(lock1, 10000)) { // Increase timeout to 10 seconds
                System.out.println(Thread.currentThread().getName() + ": lock1 acquired.");
                break;
            } else {
                System.out.println(Thread.currentThread().getName() + ": cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                randomDelay();
            }
        }
        System.out.println(Thread.currentThread().getName() + ": executing second operation.");
        lock1.unlock();
        lock2.unlock();
    }

    // helper methods
    private boolean tryLock(Lock lock, long timeout) {
        try {
            return lock.tryLock(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void randomDelay() {
        try {
            Thread.sleep(new Random().nextInt(100)); // Introduce a random delay to break symmetry
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
