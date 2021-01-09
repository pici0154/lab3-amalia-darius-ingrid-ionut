package tcp.client.service;

import tcp.client.tcp.TcpClient;
import tcp.common.BookService;
import tcp.common.Message;
import tcp.common.domain.Book;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BookServiceClientImpl implements BookService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public BookServiceClientImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> findAllBooks() {
        return executorService.submit(() -> {
            Message request = new Message(BookService.GET_ALL_BOOKS, "getBooks");

            Message response = tcpClient.sendAndReceive(request);
            String result = (String) response.getBody();
            return result;
        });
    }

    @Override
    public Future<Integer> addBook(Book book) {
        return executorService.submit(() -> {
            Message request = new Message<>(BookService.ADD, book);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }

    @Override
    public Future<Optional<Book>> delete(Integer id) {
        return executorService.submit(() -> {
            Message request = new Message<>(BookService.DELETE, id);
            Message response = tcpClient.sendAndReceive(request);
            Optional<Book> deletedBook = Optional.ofNullable((Book) response.getBody());
            return deletedBook;
        });
    }

    @Override
    public Future<Integer> update(Book book) {
        return executorService.submit(() -> {
            Message request = new Message<>(BookService.UPDATE, book);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }
}
