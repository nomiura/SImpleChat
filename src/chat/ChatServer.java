package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private List<Client> clients = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(9999);
    }

    public void sendAll(String message) {
        for (Client client : clients) {
            client.receive(message);
        }
    }

    public void run() {
        while (true) {
            System.out.println("Waiting for connection...");

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Cliend connected");
                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }
}
