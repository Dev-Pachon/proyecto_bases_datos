package application.banco.service;

import application.banco.model.Departamento;

import java.util.List;

public interface IDepartamentoService {

    public Departamento crearDepartamento(Departamento departamento);

    public Departamento actualizarDepartamento(Departamento departamento);

    public Departamento eliminarDepartamento(Integer id);

    public Departamento buscarPorId(Integer id);

    public List<Departamento> buscarTodos();
}
