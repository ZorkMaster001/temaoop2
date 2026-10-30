package ro.umfst.biblioteca.persistence;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T> findAll() throws SQLException;
    void save(T element) throws SQLException;
    void delete(String id) throws SQLException;
}