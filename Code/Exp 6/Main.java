import java.util.concurrent.Semaphore;

class MasterSlaveMutualExclusion {
    private Semaphore mutex;
    private boolean isMaster;

    public MasterSlaveMutualExclusion(boolean isMaster) {
        this.mutex = new Semaphore(1);
        this.isMaster = isMaster;
    }

    public void enterCriticalSection() {
        try {
            if (isMaster) {
                mutex.acquire();
            } else {
                // Slave nodes request permission from the inster
                requestPermissionFromMaster();
            }
            System.out.println(Thread.currentThread().getName() + " is entering the critical section.");
            Thread.sleep(1000); // Simulating some work inside the critical section
            System.out.println(Thread.currentThread().getName() + " is leaving the critical section.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (isMaster) {
                mutex.release();
            }
        }
    }

    private void requestPermissionFromMaster() throws InterruptedException {
        // Here, we can implement communication with the master node to request permission
        // For simplicity, we'll just wait until the master is available
        while (!mutex.tryAcquire()) {
            Thread.sleep(100); // Retry after some time
        }
        mutex.release(); // Immediately release to maintain mutual exclusion
    }
}

class Node implements Runnable {
    private MasterSlaveMutualExclusion mutex;

    public Node(MasterSlaveMutualExclusion mutex) {
        this.mutex = mutex;
    }

    @Override
    public void run() {
        mutex.enterCriticalSection();
    }
}

public class Main {
    public static void main(String[] args) {
        MasterSlaveMutualExclusion mutex = new MasterSlaveMutualExclusion(true); // Assume the first node is the master

        // Create slave nodes
        Thread[] slaves = new Thread[5];
        for (int i = 0; i < slaves.length; i++) {
            slaves[i] = new Thread(new Node(new MasterSlaveMutualExclusion(false)), "Slave-" + i);
        }

        // Start slave threads
        for (Thread slave : slaves) {
            slave.start();
        }

        // Simulate master work
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Master releases the resource
        mutex.enterCriticalSection();
    }
}
