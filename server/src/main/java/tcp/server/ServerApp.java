package tcp.server;

import tcp.common.BookService;
import tcp.common.ClientService;
import tcp.common.ApartmentService;
import tcp.common.Message;
import tcp.common.Service;
import tcp.common.domain.Apartment;
import tcp.common.domain.Book;
import tcp.common.domain.Client;
import tcp.common.domain.validators.ApartamentValidator;
import tcp.common.domain.validators.BookValidator;
import tcp.common.domain.validators.ClientValidator;
import tcp.common.domain.validators.IValidator;
import tcp.server.repository.ApartmentDataBaseRepository;
import tcp.server.repository.BookDataBaseRepository;
import tcp.server.repository.ClientDataBaseRepozitory;
import tcp.server.service.ApartmentServiceImpl;
import tcp.server.service.BookServiceServerImpl;
import tcp.server.service.ClientServiceServerImpl;
import tcp.server.tcp.TcpServer;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        TcpServer tcpServer = new TcpServer(executorService, Service.SERVER_PORT);

        IValidator<Book> bookValidator = new BookValidator();
        BookDataBaseRepository bookRepository = new BookDataBaseRepository(bookValidator);
        BookService bookService = new BookServiceServerImpl(executorService, bookRepository);

        IValidator<Client> clientValidator = new ClientValidator();
        ClientDataBaseRepozitory clientRepository = new ClientDataBaseRepozitory(clientValidator);
        ClientService clientService = new ClientServiceServerImpl(executorService, clientRepository);

        IValidator<Apartment> apartamentValidator = new ApartamentValidator();
        ApartmentDataBaseRepository apartmentRepository = new ApartmentDataBaseRepository(apartamentValidator);
        ApartmentService apartamentService = new ApartmentServiceImpl(executorService, apartmentRepository);

        tcpServer.addHandler(
                bookService.GET_ALL_BOOKS, (request) -> {

                    Future<String> result =
                            bookService.findAllBooks();
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                bookService.ADD, (request) -> {
                    Book book = (Book) request.getBody();
                    Future<Integer> result = bookService.addBook(book);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                bookService.DELETE, (request) -> {
                    Integer id = Integer.valueOf((Integer) request.getBody());
                    Future<Optional<Book>> result = bookService.delete(id);
                    try {
                        Book book = result.get().orElse(null);
                        return new Message(Message.OK, book);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, "");
                    }
                });

        tcpServer.addHandler(
                bookService.UPDATE, (request) -> {
                    Book book = (Book) request.getBody();
                    Future<Integer> result = bookService.update(book);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                clientService.GET_ALL_CLIENTS, (request) -> {
                    Future<String> result =
                            clientService.findAllClients();
                    try {
                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                clientService.ADD, (request) -> {
                    Client client = (Client) request.getBody();
                    Future<Integer> result = clientService.addClient(client);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                clientService.DELETE, (request) -> {
                    Integer id = Integer.valueOf((Integer) request.getBody());
                    Future<Optional<Client>> result = clientService.delete(id);
                    try {
                        Client client = result.get().orElse(null);
                        return new Message(Message.OK, client);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, "");
                    }
                });

        tcpServer.addHandler(
                clientService.UPDATE, (request) -> {
                    Client client = (Client) request.getBody();
                    Future<Integer> result = clientService.update(client);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                apartamentService.GET_ALL_APARTMENT, (request) -> {
                    Future<String> result =
                            apartamentService.findAllApartments();
                    try {
                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                apartamentService.ADD_APARTMENT, (request) -> {
                    Apartment client = (Apartment) request.getBody();
                    Future<Integer> result = apartamentService.addApartment(client);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                apartamentService.DELETE_APARTMENT, (request) -> {
                    Integer id = Integer.valueOf((Integer) request.getBody());
                    Future<Optional<Apartment>> result = apartamentService.deleteApartment(id);
                    try {
                        Apartment client = result.get().orElse(null);
                        return new Message(Message.OK, client);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, "");
                    }
                });

        tcpServer.addHandler(
                apartamentService.UPDATE_APARTMENT, (request) -> {
                    Apartment client = (Apartment) request.getBody();
                    Future<Integer> result = apartamentService.updateApartment(client);
                    try {

                        return new Message(Message.OK, result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });




        tcpServer.startServer();

        System.out.println("bye server");
    }

}