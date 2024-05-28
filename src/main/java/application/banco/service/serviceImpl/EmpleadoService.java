package application.banco.service.serviceImpl;

import application.banco.model.Empleado;
import application.banco.model.Empleado;
import application.banco.repository.repositoryImpl.EmpleadoRepository;
import application.banco.service.IEmpleadoService;

import java.util.List;

public class EmpleadoService implements IEmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService() {
        this.empleadoRepository = new EmpleadoRepository();
    }

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
        return empleado;
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado) {
        Empleado toSave = empleadoRepository.findbyId(empleado.getCodigo());
        if (toSave == null) {
            return null;
        }

        empleadoRepository.update(empleado);

        return empleado;
    }

    @Override
    public Empleado eliminarEmpleado(Integer id) {
        Empleado toDelete = empleadoRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        empleadoRepository.delete(id);
        return null;
    }

    @Override
    public Empleado buscarPorId(Integer id) {
        return empleadoRepository.findbyId(id);
    }

    @Override
    public List<Empleado> buscarTodos() {
        return empleadoRepository.findAll();
    }
}
