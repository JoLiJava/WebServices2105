import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {                                                   // KLIENT

    public static void main(String[] args) {    // 178.174.162.51 | localhost - 127.0.0.1
        try {
            Socket socket = new Socket("localhost", 5050);      // växel

            var output = new PrintWriter(socket.getOutputStream());
            output.println("Hello from client");
            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
