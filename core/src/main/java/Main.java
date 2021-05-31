import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {                                                     // SERVER

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5050)) {  // växel
            while (true) {
                Socket clientSocket = serverSocket.accept();        // anslutning
                //Starta tråd
                //Thread thread = new Thread(() -> handleConnection(clientSocket));
                //thread.start();
                executorService.submit(() -> handleConnection(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket clientSocket) {
        try {
            System.out.println(clientSocket.getInetAddress());
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(500);
            var inputFromClient
                    = new BufferedReader(
                    new InputStreamReader((clientSocket.getInputStream())));

            while (true) {
                var line = inputFromClient.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }

            var outputToClient
                    = new PrintWriter(clientSocket.getOutputStream());
            outputToClient.print(
                    "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n");
            outputToClient.flush();
            outputToClient.close();
            inputFromClient.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
