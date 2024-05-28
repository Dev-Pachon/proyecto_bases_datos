package application.banco.service;

import application.banco.model.Empleado;

import java.util.List;

public interface IEmpleadoService {

    public Empleado crearEmpleado(Empleado empleado);

    public Empleado actualizarEmpleado(Empleado empleado);

    public Empleado eliminarEmpleado(Integer id);

    public Empleado buscarPorId(Integer id);

    public List<Empleado> buscarTodos();
}
