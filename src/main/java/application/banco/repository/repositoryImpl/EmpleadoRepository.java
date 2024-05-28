package application.banco.repository.repositoryImpl;

import application.banco.model.Empleado;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class EmpleadoRepository extends RepositoryWrapper<Integer, Empleado> {
    @Override
    public List<Empleado> findAll() {
        return List.of();
    }

    @Override
    public Empleado findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Empleado empleado) {
    }

    @Override
    public void delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Empleado empleado) {
        return null;
    }
}
