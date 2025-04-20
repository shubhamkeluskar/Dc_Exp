import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Chat_Interface extends Remote {
    public void sendToServer(String message) throws RemoteException;
}