package application.banco.repository.repositoryImpl;

import application.banco.model.Prioridad;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class PrioridadRepository extends RepositoryWrapper<Integer, Prioridad> {
    @Override
    public List<Prioridad> findAll() {
        return List.of();
    }

    @Override
    public Prioridad findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Prioridad prioridad) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Prioridad prioridad) {

    }
}
