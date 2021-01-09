package tcp.client.service;

import tcp.client.tcp.TcpClient;
import tcp.common.ClientService;
import tcp.common.Message;
import tcp.common.domain.Client;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientServiceImpl implements ClientService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ClientServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> findAllClients() {
        return executorService.submit(() -> {
            Message request = new Message(ClientService.GET_ALL_CLIENTS, "getClients");

            Message response = tcpClient.sendAndReceive(request);
            String result = (String) response.getBody();
            return result;
        });
    }

    @Override
    public Future<Integer> addClient(Client client) {
        return executorService.submit(() -> {
            Message request = new Message<>(ClientService.ADD, client);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }

    @Override
    public Future<Optional<Client>> delete(Integer id) {
        return executorService.submit(() -> {
            Message request = new Message<>(ClientService.DELETE, id);
            Message response = tcpClient.sendAndReceive(request);
            Optional<Client> deletedClient = Optional.ofNullable((Client) response.getBody());
            return deletedClient;
        });
    }

    @Override
    public Future<Integer> update(Client client) {
        return executorService.submit(() -> {
            Message request = new Message<>(ClientService.UPDATE, client);
            Message response = tcpClient.sendAndReceive(request);

            Integer save = (Integer) response.getBody();
            return save;
        });
    }

}
