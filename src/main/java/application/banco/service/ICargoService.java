package application.banco.service;

import application.banco.model.Cargo;

import java.util.List;

public interface ICargoService {
    public Cargo crearCargo(Cargo cargo);

    public Cargo actualizarCargo(Cargo cargo);

    public Cargo eliminarCargo(Integer id);

    public Cargo buscarPorId(Integer id);

    public List<Cargo> buscarTodos();
}
