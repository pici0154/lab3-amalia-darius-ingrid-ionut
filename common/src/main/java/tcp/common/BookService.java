package tcp.common;

import tcp.common.domain.Book;

import java.util.Optional;
import java.util.concurrent.Future;

public interface BookService extends Service {
    String GET_ALL_BOOKS = "getBooks";
    Future<String> findAllBooks();

    String ADD = "addBook";
    Future <Integer> addBook(Book book);

    String DELETE = "deleteBook";
    Future<Optional<Book>> delete(Integer id);

    String UPDATE = "updateBook";
    Future<Integer> update(Book book);

}
