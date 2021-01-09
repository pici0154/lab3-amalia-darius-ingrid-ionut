package tcp.client.service;

import tcp.client.tcp.TcpClient;
import tcp.common.ApartmentService;
import tcp.common.BookService;
import tcp.common.Message;
import tcp.common.domain.Apartment;
import tcp.common.domain.Book;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ApartmentServiceClientImpl implements ApartmentService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ApartmentServiceClientImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> findAllApartments() {
        return executorService.submit(() -> {
            Message request = new Message(ApartmentService.GET_ALL_APARTMENT, "getApartments");

            Message response = tcpClient.sendAndReceive(request);
            String result = (String) response.getBody();
            return result;
        });
    }

    @Override
    public Future<Integer> addApartment(Apartment ap) {
        return executorService.submit(() -> {
            Message request = new Message<>(ApartmentService.ADD_APARTMENT, ap);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }

    @Override
    public Future<Optional<Apartment>> deleteApartment(Integer id) {
        return executorService.submit(() -> {
            Message request = new Message<>(ApartmentService.DELETE_APARTMENT, id);
            Message response = tcpClient.sendAndReceive(request);
            Optional<Apartment> deletedBook = Optional.ofNullable((Apartment) response.getBody());
            return deletedBook;
        });
    }

    @Override
    public Future<Integer> updateApartment(Apartment apartment) {
        return executorService.submit(() -> {
            Message request = new Message<>(ApartmentService.UPDATE_APARTMENT, apartment);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }
}
