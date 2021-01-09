package tcp.server.tcp;

import tcp.common.Message;
import tcp.common.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {
    private ExecutorService executorService;
    private int port;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService, int port) {
        this.executorService = executorService;
        this.port = port;
        this.methodHandlers = new HashMap<>();
    }

    public void addHandler(String key, UnaryOperator<Message> handler) {
        methodHandlers.put(key, handler);
    }

    public void startServer() {
        System.out.println("starting server");
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("could not start server", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            try (var os = new ObjectOutputStream(clientSocket.getOutputStream());
                 var is = new ObjectInputStream(clientSocket.getInputStream())) {

                Message request = Message.readFrom(is);
                System.out.println("server - received request: " + request);
                UnaryOperator<Message> handler = methodHandlers.get(request.getHeader());
                Message response = handler.apply(request);
                System.out.println("server - computed response: " + response);

                response.writeTo(os);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
