package application.banco.service;

import application.banco.model.Prioridad;

import java.util.List;

public interface IPrioridadService {
    public Prioridad crearPrioridad(Prioridad prioridad);

    public Prioridad actualizarPrioridad(Prioridad prioridad);

    public Prioridad eliminarPrioridad(int id);

    public Prioridad buscarPorId(int id);

    public List<Prioridad> buscarTodos();
}
