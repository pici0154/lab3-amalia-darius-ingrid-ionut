package tcp.client;

import tcp.client.service.ApartmentServiceClientImpl;
import tcp.client.service.BookServiceClientImpl;
import tcp.client.service.ClientServiceImpl;
import tcp.client.tcp.TcpClient;
import tcp.client.ui.Console;
import tcp.common.ApartmentService;
import tcp.common.BookService;
import tcp.common.ClientService;
import tcp.common.Service;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient(Service.SERVER_HOST, Service.SERVER_PORT);
        BookService bookService = new BookServiceClientImpl(executorService, tcpClient);
        ClientService clientService = new ClientServiceImpl(executorService, tcpClient);
        ApartmentService apartmentService = new ApartmentServiceClientImpl(executorService, tcpClient) ;

        Console console = new Console(bookService, clientService, apartmentService);

        console.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");
    }
}


