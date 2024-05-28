package application.banco.service.serviceImpl;

import application.banco.error.CustomError;
import application.banco.model.Departamento;
import application.banco.repository.repositoryImpl.DepartamentoRepository;
import application.banco.service.IDepartamentoService;

import java.util.List;

public class DepartamentoService implements IDepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService() {
        this.departamentoRepository = new DepartamentoRepository();
    }

    @Override
    public Departamento crearDepartamento(Departamento departamento) {
        departamentoRepository.save(departamento);
        return departamento;
    }

    @Override
    public Departamento actualizarDepartamento(Departamento departamento) {
        Departamento toSave = departamentoRepository.findbyId(departamento.getCodigo());
        if (toSave == null) {
            return null;
        }

        departamentoRepository.update(departamento);

        return departamento;
    }

    @Override
    public Departamento eliminarDepartamento(Integer id) throws CustomError {
        Departamento toDelete = departamentoRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        departamentoRepository.delete(id);
        return null;
    }

    @Override
    public Departamento buscarPorId(Integer id) {
        return departamentoRepository.findbyId(id);

    }

    @Override
    public List<Departamento> buscarTodos() {
        return departamentoRepository.findAll();
    }
}
