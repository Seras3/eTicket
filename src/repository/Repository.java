package repository;

public interface Repository<T> {

    T get(String id);

    void add(T obj);

    void update(T obj);

    void delete(T obj);
}
