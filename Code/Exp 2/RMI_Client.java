import java.rmi.Naming;
import java.util.Scanner;

public class RMI_Client {

    static Scanner input = null;

    public static void main(String[] args) throws Exception {
        RMI_Chat_Interface chatapi = (RMI_Chat_Interface)

        Naming.lookup("rmi://localhost:6000/chat");
        input = new Scanner(System.in);
        System.out.println("Connected to server...");

        System.out.println("Type a message for sending to server...");
        String message = input.nextLine();
        while (!message.equals("Bye")) {
            chatapi.sendToServer(message);
            message = input.nextLine();
        }
    }
}