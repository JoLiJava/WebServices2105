import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {                                                   // CLIENT

    public static void main(String[] args) {
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
