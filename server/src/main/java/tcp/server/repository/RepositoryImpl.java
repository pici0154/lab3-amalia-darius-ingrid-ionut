package tcp.server.repository;


import org.xml.sax.SAXException;
import tcp.common.domain.BaseEntity;
import tcp.common.domain.validators.IValidator;
import tcp.common.domain.validators.ValidatorException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryImpl<ID,T extends BaseEntity<ID>> implements IRepository<ID, T> {
    private Map<ID, T> entities;
    private IValidator<T> validator;

    public RepositoryImpl(IValidator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Iterable<T> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public Optional save(T entity) throws ValidatorException, ParserConfigurationException, SAXException, IOException {
        if (entity == null) {
            throw new ValidatorException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
