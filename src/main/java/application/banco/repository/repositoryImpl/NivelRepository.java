package application.banco.repository.repositoryImpl;

import application.banco.model.Nivel;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class NivelRepository extends RepositoryWrapper<Integer, Nivel> {
    @Override
    public List<Nivel> findAll() {
        return List.of();
    }

    @Override
    public Nivel findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Nivel nivel) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Nivel nivel) {

    }
}
