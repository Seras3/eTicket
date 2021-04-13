package dao;

import util.Converter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.Map;

public interface DAO<T> {

    T get(String id);

    List<T> getAll();

    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);

}
