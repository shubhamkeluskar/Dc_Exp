import java.util.ArrayList;
import java.util.List;

// Represents a node in the distributed system
class Node {
    private int id;
    private int workload;

    public Node(int id) {
        this.id = id;
        this.workload = 0;
    }

    public int getId() {
        return id;
    }

    public int getWorkload() {
        return workload;
    }

    public void assignTask(int taskWorkload) {
        this.workload += taskWorkload;
    }

    @Override
    public String toString() {
        return "Node " + id + " (Workload: " + workload + ")";
    }
}

// Represents a task to be executed in the distributed system
class Task {
    private int workload;

    public Task(int workload) {
        this.workload = workload;
    }

    public int getWorkload() {
        return workload;
    }
}

// Load balancing algorithm for distributing tasks among nodes
class LoadBalancer {
    private List<Node> nodes;

    public LoadBalancer(List<Node> nodes) {
        this.nodes = nodes;
    }

    // Assigns a task to the least loaded node
    public void assignTask(Task task) {
        Node leastLoadedNode = nodes.get(0);
        for (Node node : nodes) {
            if (node.getWorkload() < leastLoadedNode.getWorkload()) {
                leastLoadedNode = node;
            }
        }
        leastLoadedNode.assignTask(task.getWorkload());
        System.out.println("\n");
        System.out.println("Assigned Task with Workload " + task.getWorkload() + " to " + leastLoadedNode);
        System.out.println("\n");
    }
}

public class DistributedLoadBalancer {
    public static void main(String[] args) {
        // Create nodes
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            nodes.add(new Node(i));
        }

        // Initialize load balancer
        LoadBalancer loadBalancer = new LoadBalancer(nodes);

        // Create tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(30));
        tasks.add(new Task(40));
        tasks.add(new Task(55));
        tasks.add(new Task(75));

        // Assign tasks to nodes using load balancer
        for (Task task : tasks) {
            loadBalancer.assignTask(task);
        }
    }
}
