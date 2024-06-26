package application.banco.repository;

import application.banco.error.CustomError;

import java.util.List;

public interface IRepository<T, V> {

    public List<V> findAll();

    public V findbyId(T t);

    public void save(V v);

    public void delete(T t) throws CustomError;

    public void update(V v);
}
