package repository;

import util.CustomDatabaseException;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    List<T> getAll() throws CustomDatabaseException, SQLException;

    T getById(int id) throws CustomDatabaseException, SQLException;

    void save(T object) throws CustomDatabaseException, SQLException;

    void remove(T id) throws CustomDatabaseException, SQLException;


}
