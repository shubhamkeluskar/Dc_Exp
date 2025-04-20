import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_Chat_Interface {

    public RMI_Server() throws RemoteException {
        super();
    }

    @Override
    public void sendToServer(String message) throws RemoteException {
        System.out.println("Client says: " + message);
    }

    public static void main(String[] args) throws Exception {
        Registry rmiregistry = LocateRegistry.createRegistry(6000);
        rmiregistry.bind("chat", new RMI_Server());
        System.out.println("Chat server is running...");
    }
}