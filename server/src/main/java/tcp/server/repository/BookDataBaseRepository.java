package tcp.server.repository;

import org.xml.sax.SAXException;
import tcp.common.domain.Book;
import tcp.common.domain.validators.IValidator;
import tcp.common.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDataBaseRepository extends RepositoryImpl<Integer, Book> {
    private static final String URL = "jdbc:postgresql://localhost:5432/bookstore_tcp";
    private static final String USER = "postgres";// System.getProperty("user");
    private static final String PASSWORD = "1234"; //System.getProperty("password");

    /**
     * Creates a repository for a specific type implementation object.
     *
     * @param validator
     */
    public BookDataBaseRepository(IValidator<Book> validator) {
        super(validator);
        List<Book> books = this.findAll();

        books.forEach(book -> {
            try {
                super.save(book);
            } catch (ValidatorException | ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });
    }


    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String yearofpublication = rs.getString("yearofpublication");
                double price = rs.getDouble("price");

                Book book = new Book(id, name, author, publisher, yearofpublication, price);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Book> save(Book entity) throws IOException, SAXException, ParserConfigurationException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToDataBase(entity);
        return Optional.empty();
    }

    public void saveToDataBase(Book book) {

        String sql = "INSERT INTO book (id, name, author, publisher, yearOfPublication, price) VALUES (?, ?, ?, ?, ? ,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, book.getId());
            ps.setString(2, book.getName());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getYearOfPublication());
            ps.setDouble(6, book.getPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Book> delete(Integer id) {

        Optional<Book> optional = super.delete(id);

        if (optional.isPresent())
            deleteFromDatabase(id);

        return optional;
    }

    public void deleteFromDatabase(Integer id) {

        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Book> update(Book book){
        Optional<Book> optional = null;
        try {
            optional = super.update(book);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }

        if (optional.isPresent()) {
            updateToDataBase(book);
            return optional;
        }

        return Optional.empty();
    }

    private void updateToDataBase(Book book) {
        String sql = "UPDATE book SET name=?, author=?, publisher=?, yearOfPublication=?, price=? WHERE id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getPublisher());
            ps.setString(4, book.getYearOfPublication());
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
