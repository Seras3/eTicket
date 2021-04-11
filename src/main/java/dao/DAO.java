package dao;

import java.util.HashMap;
import java.util.List;

public interface DAO<T> {

    T get(String id);

    List<T> getAll();

    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);

}
