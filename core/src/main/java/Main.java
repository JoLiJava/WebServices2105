import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        // localhost - 127.0.0.1
        // 178.174.162.51

        try (ServerSocket socket = new ServerSocket(5050)) {
            Socket client = socket.accept();
            System.out.println(client.getInetAddress());

            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));

            while (true) {
                System.out.println(inputFromClient.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
