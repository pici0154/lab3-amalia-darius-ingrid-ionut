package tcp.server.repository;

import org.xml.sax.SAXException;
import tcp.common.domain.Apartment;
import tcp.common.domain.validators.IValidator;
import tcp.common.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentDataBaseRepository extends RepositoryImpl<Integer, Apartment> {
    private static final String URL = "jdbc:postgresql://localhost:5432/bookstore_tcp";
    private static final String USER = "postgres";// System.getProperty("user");
    private static final String PASSWORD = "1234"; //System.getProperty("password");

    /**
     * Creates a repository for a specific type implementation object.
     *
     * @param validator
     */
    public ApartmentDataBaseRepository(IValidator<Apartment> validator) {
        super(validator);
        List<Apartment> apartments = this.findAllApartments();

        apartments.forEach(apartment -> {
            try {
                super.save(apartment);
            } catch (ValidatorException | ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });
    }


    public List<Apartment> findAllApartments() {
        List<Apartment> apartments = new ArrayList<>();

        String sql = "SELECT * FROM apartments";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String address = rs.getString("address");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String yearofconstruction = rs.getString("yearofconstruction");


                Apartment ap = new Apartment(id, address, category, description, yearofconstruction, price);
                apartments.add(ap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }

    @Override
    public Optional<Apartment> save(Apartment entity) throws IOException, SAXException, ParserConfigurationException {
        Optional<Apartment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToDataBase(entity);
        return Optional.empty();
    }

    public void saveToDataBase(Apartment ap) {

        String sql = "INSERT INTO apartments (id, " +
                "address, category, description,  " +
                " price, yearOfConstruction ) VALUES (?, ?, ?, ?, ? ,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, ap.getId());
            ps.setString(3, ap.getAddress());
            ps.setString(4, ap.getCategory());
            ps.setString(2, ap.getDescription());
            ps.setDouble(5, ap.getPrice());
            ps.setString(6, ap.getYearOfConstruction());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Apartment> delete(Integer id) {

        Optional<Apartment> optional = super.delete(id);

        if (optional.isPresent())
            deleteFromDatabase(id);

        return optional;
    }

    public void deleteFromDatabase(Integer id) {

        String sql = "DELETE FROM apartments WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Apartment> update(Apartment apartment){
        Optional<Apartment> optional = null;
        try {
            optional = super.update(apartment);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }

        if (optional.isPresent()) {
            updateToDataBase(apartment);
            return optional;
        }

        return Optional.empty();
    }

    private void updateToDataBase(Apartment ap) {
        String sql = "UPDATE apartments SET address=?, " +
                "category=?, description=?, price=?, yearOfConstruction=?," +
                "  WHERE id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {


            ps.setString(1, ap.getAddress());
            ps.setString(2, ap.getCategory());
            ps.setString(3, ap.getDescription());
            ps.setDouble(4, ap.getPrice());
            ps.setString(5, ap.getYearOfConstruction());
            ps.setInt(6, ap.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
