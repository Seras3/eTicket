package dao;

import java.util.HashMap;
import java.util.List;

public interface DAO<T> extends Convertible<T> {

    T get(String id);

    List<T> getAll();

    void add(T t);

    void update(T t);

    void delete(T t);

}
