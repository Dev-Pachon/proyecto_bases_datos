package application.banco.repository.repositoryImpl;

import application.banco.model.Departamento;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class DepartamentoRepository extends RepositoryWrapper<Integer, Departamento> {
    @Override
    public List<Departamento> findAll() {
        return List.of();
    }

    @Override
    public Departamento findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Departamento departamento) {
    }

    @Override
    public void delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Departamento departamento) {
        return null;
    }
}
