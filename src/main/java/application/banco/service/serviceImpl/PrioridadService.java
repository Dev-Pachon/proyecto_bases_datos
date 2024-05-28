package application.banco.service.serviceImpl;

import application.banco.model.Prioridad;
import application.banco.model.Prioridad;
import application.banco.repository.repositoryImpl.BitacoraRepository;
import application.banco.repository.repositoryImpl.PrioridadRepository;
import application.banco.service.IPrioridadService;

import java.util.List;

public class PrioridadService implements IPrioridadService {

    private final PrioridadRepository prioridadRepository;

    public PrioridadService() {
        this.prioridadRepository = new PrioridadRepository();
    }
    @Override
    public Prioridad crearPrioridad(Prioridad prioridad) {
        prioridadRepository.save(prioridad);
        return prioridad;
    }

    @Override
    public Prioridad actualizarPrioridad(Prioridad prioridad) {
        Prioridad toSave = prioridadRepository.findbyId(prioridad.getCodigo());
        if (toSave == null) {
            return null;
        }

        prioridadRepository.update(prioridad);

        return prioridad;
    }

    @Override
    public Prioridad eliminarPrioridad(int id) {
        Prioridad toDelete = prioridadRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        prioridadRepository.delete(id);
        return null;    }

    @Override
    public Prioridad buscarPorId(int id) {
        return prioridadRepository.findbyId(id);
    }

    @Override
    public List<Prioridad> buscarTodos() {
        return prioridadRepository.findAll();
    }
}
