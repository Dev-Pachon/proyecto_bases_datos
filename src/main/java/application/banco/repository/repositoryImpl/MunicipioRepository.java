package application.banco.repository.repositoryImpl;

import application.banco.model.Municipio;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class MunicipioRepository extends RepositoryWrapper<Integer, Municipio> {
    @Override
    public List<Municipio> findAll() {
        return List.of();
    }

    @Override
    public Municipio findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Municipio municipio) {
    }

    @Override
    public void delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Municipio municipio) {
        return null;
    }
}
