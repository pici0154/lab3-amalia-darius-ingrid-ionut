package tcp.server.repository;

import org.xml.sax.SAXException;
import tcp.common.domain.BaseEntity;
import tcp.common.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Optional;

public interface IRepository <ID, T extends BaseEntity<ID>> {

    /**
     * @return all entities.
     */
    Iterable<T> findAll();

    Optional<T> save(T entity) throws ValidatorException, ParserConfigurationException, SAXException, IOException;

    Optional<T> delete(ID id);

    Optional<T> update(T entity) throws ValidatorException;

}
