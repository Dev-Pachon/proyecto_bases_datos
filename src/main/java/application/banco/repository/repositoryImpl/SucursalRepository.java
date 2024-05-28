package application.banco.repository.repositoryImpl;

import application.banco.model.Sucursal;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class SucursalRepository extends RepositoryWrapper<Integer, Sucursal> {
    @Override
    public List<Sucursal> findAll() {
        return List.of();
    }

    @Override
    public Sucursal findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Sucursal sucursal) {
    }

    @Override
    public void delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Sucursal sucursal) {
        return null;
    }
}
