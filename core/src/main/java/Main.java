import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {                                                     // SERVER

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5050)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress());

                var inputFromClient
                        = new BufferedReader(
                        new InputStreamReader((clientSocket.getInputStream())));

                while (true) {
                    var line = inputFromClient.readLine();
                    if (line.isEmpty()) {
                        break;
                    }
                    System.out.println(line);
                }

                var outputToClient = new PrintWriter(clientSocket.getOutputStream());
                outputToClient.print("HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n");
                outputToClient.flush();
                outputToClient.close();
                inputFromClient.close();
                clientSocket.close();
            }
//            String input = "";
//            while (input != null) {
//                input = inputFromClient.readLine();
//                System.out.println(input);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
