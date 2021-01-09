package tcp.common.domain.validators;

import tcp.common.domain.Apartment;

public class ApartamentValidator implements IValidator<Apartment> {

    @Override
    public void validate(Apartment book) throws ValidatorException {
        if (book.getId() <= 0) {
            throw new BookStoreException("This is an invalid id number");
        }}
    }