package application.banco.service;

import application.banco.model.Profesion;

import java.util.List;

public interface IProfesionService {
    public Profesion crearProfesion(Profesion profesion);

    public Profesion actualizarProfesion(Profesion profesion);

    public Profesion eliminarProfesion(int id);

    public Profesion buscarPorId(int id);

    public List<Profesion> buscarTodos();
}
