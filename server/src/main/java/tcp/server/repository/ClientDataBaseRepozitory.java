package tcp.server.repository;

import org.xml.sax.SAXException;

import tcp.common.domain.Client;
import tcp.common.domain.validators.IValidator;
import tcp.common.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDataBaseRepozitory extends RepositoryImpl<Integer, Client>{
    private static final String URL = "jdbc:postgresql://localhost:5432/bookstore_tcp";
    private static final String USER = "postgres";// System.getProperty("user");
    private static final String PASSWORD = "1234"; //System.getProperty("password");

    /**
     * Creates a repository for a specific type implementation object.
     *
     * @param validator
     */
    public ClientDataBaseRepozitory(IValidator<Client> validator) {
        super(validator);
        List<Client> clients = this.findAll();

        clients.forEach(client -> {
            try {
                super.save(client);
            } catch (ValidatorException | ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM clients";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String dateOFRegistration = rs.getString("dateofregistration");

                Client client = new Client(id, name, dateOFRegistration);
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, SAXException, IOException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToDataBase(entity);
        return Optional.empty();
    }

    public void saveToDataBase(Client client) {

        String sql = "INSERT INTO clients (id, name, dateofregistration) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getDateOfRegistration());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> delete(Integer id) {

        Optional<Client> optional = super.delete(id);

        if (optional.isPresent())
            deleteFromDatabase(id);

        return optional;
    }

    public void deleteFromDatabase(Integer id) {

        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> update(Client client){
        Optional<Client> optional = null;
        try {
            optional = super.update(client);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }

        if (optional.isPresent()) {
            updateToDataBase(client);
            return optional;
        }

        return Optional.empty();
    }

    private void updateToDataBase(Client client) {
        String sql = "UPDATE clients SET name=?, dateofregistration=? WHERE id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, client.getName());
            ps.setString(2, client.getDateOfRegistration());

            ps.setInt(3, client.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
