package tcp.common.domain.validators;

import tcp.common.domain.Book;

public class BookValidator implements IValidator<Book> {

    @Override
    public void validate(Book book) throws ValidatorException {
        if (book.getId() <= 0) {
            throw new BookStoreException("This is an invalid id number");
        }

        if (book.getName() == null || book.getName().isEmpty()) {
            throw new BookStoreException("Name should be given");
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new BookStoreException("Author should be given");
        }

        if (book.getPublisher() == null || book.getPublisher().isEmpty()) {
            throw new RuntimeException("Publisher should be given");
        }

        if (book.getYearOfPublication() == null || book.getYearOfPublication().isEmpty()) {
            throw new RuntimeException("Year of Publication should be given");
        }

        if (book.getPrice() <= 0) {
            throw new RuntimeException("Price and must be > 0");
        }
    }
}
