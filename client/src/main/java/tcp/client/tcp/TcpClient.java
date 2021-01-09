package tcp.client.tcp;

import tcp.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
    private String host;
    private int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(host, port);
             var is = new ObjectInputStream(socket.getInputStream());
             var os = new ObjectOutputStream(socket.getOutputStream());
        ) {
            request.writeTo(os);
            System.out.println("ClientApp: sent request: " + request);
            Message response = Message.readFrom(is);
            System.out.println("ClientApp: received response: " + response);

            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
