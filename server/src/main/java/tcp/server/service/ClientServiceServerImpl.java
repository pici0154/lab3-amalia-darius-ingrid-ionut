package tcp.server.service;

import tcp.common.ClientService;
import tcp.common.domain.Client;
import tcp.server.repository.IRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientServiceServerImpl implements ClientService {
    private ExecutorService executorService;
    private IRepository<Integer, Client> clientRepository;

    public ClientServiceServerImpl(ExecutorService executorService, IRepository<Integer, Client> clientReository) {
        this.executorService = executorService;
        this.clientRepository = clientReository;
    }

    @Override
    public Future<String> findAllClients() {
        Iterable<Client> books = clientRepository.findAll();

        Set<Client> collect = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        return executorService.submit(() -> collect.toString());
    }

    @Override
    public Future<Integer> addClient(Client client) {
        return executorService.submit(() -> {
            Optional<Client> currentClient = this.clientRepository.save(client);
            if(currentClient.isPresent()){
                return 1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public Future<Optional<Client>> delete(Integer id) {
        return executorService.submit(() -> {
            return this.clientRepository.delete(id);
        });
    }

    @Override
    public Future<Integer> update(Client client) {
        return executorService.submit(() -> {
            Optional<Client> currentClient = this.clientRepository.update(client);
            if(currentClient.isPresent()){
                return 1;
            } else {
                return 0;
            }
        });
    }

}
