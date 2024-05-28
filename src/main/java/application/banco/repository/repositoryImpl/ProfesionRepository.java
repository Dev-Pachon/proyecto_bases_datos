package application.banco.repository.repositoryImpl;

import application.banco.model.Profesion;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class ProfesionRepository extends RepositoryWrapper<Integer, Profesion> {
    @Override
    public List<Profesion> findAll() {
        return List.of();
    }

    @Override
    public Profesion findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Profesion profesion) {
    }

    @Override
    public void delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Profesion profesion) {
        return null;
    }
}
