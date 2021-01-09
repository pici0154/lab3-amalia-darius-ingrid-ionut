package tcp.common.domain.validators;
import tcp.common.domain.Client;

public class ClientValidator implements IValidator<Client>{
    public void validate(Client client) throws ValidatorException {
        if (client.getId() <= 0) {
            throw new RuntimeException("This is an invalid id number");
        }

        if (client.getName() == null || client.getName().isEmpty()) {
            throw new RuntimeException("Name should be given");
        }

        if (client.getDateOfRegistration() == null || client.getDateOfRegistration().isEmpty()) {
            throw new RuntimeException("Date of registration should be given");
        }
    }
}
