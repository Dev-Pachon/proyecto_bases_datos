package application.banco.repository.repositoryImpl;

import application.banco.model.Funcion;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class FuncionRepository extends RepositoryWrapper<Integer, Funcion> {
    @Override
    public List<Funcion> findAll() {
        return List.of();
    }

    public List<Funcion> findAllByCargoId(Integer userId) {
        return List.of();
    }

    @Override
    public Funcion findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Funcion funcion) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Funcion funcion) {

    }
}
