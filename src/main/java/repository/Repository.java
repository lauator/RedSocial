package repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException;

    void save(T object) throws SQLException;

    void remove(Integer id) throws SQLException;


}
