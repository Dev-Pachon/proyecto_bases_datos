package application.banco.service;

import application.banco.model.Funcion;

import java.util.List;

public interface IFuncionService {

    public Funcion crearFuncion(Funcion funcion);

    public Funcion actualizarFuncion(Funcion funcion);

    public Funcion eliminarFuncion(Integer id);

    public Funcion buscarPorId(Integer id);

    public List<Funcion> buscarTodos();

}
