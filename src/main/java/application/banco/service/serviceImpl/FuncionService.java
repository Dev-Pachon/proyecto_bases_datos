package application.banco.service.serviceImpl;

import application.banco.model.Funcion;
import application.banco.repository.repositoryImpl.FuncionRepository;
import application.banco.service.IFuncionService;

import java.util.List;

public class FuncionService implements IFuncionService {

    private final FuncionRepository funcionRepository;

    public FuncionService() {
        this.funcionRepository = new FuncionRepository();
    }

    @Override
    public Funcion crearFuncion(Funcion funcion) {
        funcionRepository.save(funcion);
        return funcion;
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) {
        Funcion toSave = funcionRepository.findbyId(funcion.getCodigo());
        if (toSave == null) {
            return null;
        }

        funcionRepository.update(funcion);

        return funcion;
    }

    @Override
    public Funcion eliminarFuncion(Integer id) {
        Funcion toDelete = funcionRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        funcionRepository.delete(id);
        return null;
    }

    @Override
    public Funcion buscarPorId(Integer id) {
        return funcionRepository.findbyId(id);
    }

    @Override
    public List<Funcion> buscarTodos() {
        return funcionRepository.findAll();
    }
}
