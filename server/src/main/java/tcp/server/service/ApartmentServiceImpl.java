package tcp.server.service;

import tcp.common.ApartmentService;
import tcp.common.domain.Apartment;
import tcp.server.repository.IRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ApartmentServiceImpl implements ApartmentService {
    private ExecutorService executorService;
    private IRepository<Integer, Apartment> apartmentRepository;

    public ApartmentServiceImpl(ExecutorService executorService, IRepository<Integer, Apartment> apartmentRepository) {
        this.executorService = executorService;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public Future<String> findAllApartments() {
        Iterable<Apartment> books = apartmentRepository.findAll();

        Set<Apartment> collect = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        return executorService.submit(() -> collect.toString());
    }

    @Override
    public Future<Integer> addApartment(Apartment book) {
        return executorService.submit(() -> {
            Optional<Apartment> currentBook = this.apartmentRepository.save(book);
            if (currentBook.isPresent()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public Future<Optional<Apartment>> deleteApartment(Integer id) {
        return executorService.submit(() -> {
            return this.apartmentRepository.delete(id);
        });
    }

    @Override
    public Future<Integer> updateApartment(Apartment ap) {
        return executorService.submit(() -> {
            Optional<Apartment> currentApartment = this.apartmentRepository.update(ap);
            if (currentApartment.isPresent()) {
                return 1;
            }
            return 0;

        });
    }
}

