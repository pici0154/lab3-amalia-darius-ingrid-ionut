package tcp.server.service;

import tcp.common.BookService;

import tcp.common.domain.Book;
import tcp.server.repository.IRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookServiceServerImpl implements BookService {
    private ExecutorService executorService;
    private IRepository<Integer, Book> bookRepository;

    public BookServiceServerImpl(ExecutorService executorService, IRepository<Integer, Book> bookRepository) {
        this.executorService = executorService;
        this.bookRepository = bookRepository;
    }

    @Override
    public Future<String> findAllBooks() {
        Iterable<Book> books = bookRepository.findAll();

        Set<Book> collect = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        return executorService.submit(() -> collect.toString());
    }

    @Override
    public Future<Integer> addBook(Book book) {
        return executorService.submit(() -> {
            Optional<Book> currentBook = this.bookRepository.save(book);
            if (currentBook.isPresent()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public Future<Optional<Book>> delete(Integer id) {
        return executorService.submit(() -> {
            return this.bookRepository.delete(id);
        });
    }

    @Override
    public Future<Integer> update(Book book) {
        return executorService.submit(() -> {
            Optional<Book> currentBook = this.bookRepository.update(book);
            if (currentBook.isPresent()) {
                return 1;
            }
            return 0;

        });
    }
}
