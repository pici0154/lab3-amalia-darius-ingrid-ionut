package tcp.common;

import tcp.common.domain.Client;

import java.util.Optional;
import java.util.concurrent.Future;

public interface ClientService extends Service{
    String GET_ALL_CLIENTS = "getClients";
    Future<String> findAllClients ();

    String ADD = "addClient";
    Future <Integer> addClient(Client client);

    String DELETE = "deleteClient";
    Future<Optional<Client>> delete(Integer id);

    String UPDATE = "updateClient";
    Future<Integer> update(Client client);
}
