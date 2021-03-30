package dao;

import java.util.List;

public interface Convertible<T> {
    List<String[]> convert(List<T> lt);
}
