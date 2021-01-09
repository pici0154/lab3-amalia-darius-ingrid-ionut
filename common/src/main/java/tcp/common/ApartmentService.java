package tcp.common;

import tcp.common.domain.Apartment;
import tcp.common.domain.Book;

import java.util.Optional;
import java.util.concurrent.Future;

public interface ApartmentService extends Service {
    String GET_ALL_APARTMENT = "getApartments";
    Future<String> findAllApartments();

    String ADD_APARTMENT = "addApartment";
    Future <Integer> addApartment(Apartment apartment);

    String DELETE_APARTMENT = "deleteApartment";
    Future<Optional<Apartment>> deleteApartment(Integer id);

    String UPDATE_APARTMENT = "updateApartment";
    Future<Integer> updateApartment(Apartment apartment);
}
