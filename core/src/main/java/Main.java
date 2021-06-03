import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {                                                     //SERVER


    public static List<String> billboard = new ArrayList<>();


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5050)) {  //växel
            while (true) {
                Socket clientSocket = serverSocket.accept();        //anslutning
                executorService.submit(() -> handleConnection(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket clientSocket) {
        try {
            var inputFromClient
                    = new BufferedReader(
                    new InputStreamReader((clientSocket.getInputStream())));

            readRequest(inputFromClient);

            var outputToClient
                    = new PrintWriter(clientSocket.getOutputStream());
            sendResponse(outputToClient);

            outputToClient.close();
            inputFromClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(PrintWriter outputToClient) {
        //outputToClient.print(
        //"HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n");

        synchronized (billboard) {
            for (String line : billboard) {
                outputToClient.print(line + "\r\n");
            }
        }
        outputToClient.println("\r\n");
        outputToClient.flush();
    }

    private static void readRequest(BufferedReader inputFromClient) throws IOException {
        List<String> tempList = new ArrayList<>();

        while (true) {
            var line = inputFromClient.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            tempList.add(line);
            System.out.println(line);
        }

        synchronized (billboard) {
            billboard.addAll(tempList);
        }
    }
}
