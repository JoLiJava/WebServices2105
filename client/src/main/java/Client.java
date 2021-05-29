import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {                                                   // KLIENT

    public static void main(String[] args) {    // 178.174.162.51 |
        var bytes = System.lineSeparator().getBytes(); // localhost - 127.0.0.1
        for (byte b : bytes) {
            System.out.println(b);
        }

        try {
            Socket clientSocket = new Socket("localhost", 5050);      // växel

            var output = new PrintWriter(clientSocket.getOutputStream());
            output.print("Hello from client\r\n\r\n");
            output.flush();

            //Läs svaret från servern
            var inputFromServer
                    = new BufferedReader(
                    new InputStreamReader((clientSocket.getInputStream())));

            while (true) {
                var line = inputFromServer.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }
            clientSocket.close();
            output.close();
            inputFromServer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
