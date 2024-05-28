package application.banco.service.serviceImpl;

import application.banco.model.Profesion;
import application.banco.model.Profesion;
import application.banco.repository.repositoryImpl.ProfesionRepository;
import application.banco.service.IProfesionService;

import java.util.List;

public class ProfesionService implements IProfesionService {

    private final ProfesionRepository profesionRepository;

    public ProfesionService(){
        this.profesionRepository = new ProfesionRepository();
    }

    @Override
    public Profesion crearProfesion(Profesion profesion) {
        profesionRepository.save(profesion);
        return profesion;
    }

    @Override
    public Profesion actualizarProfesion(Profesion profesion) {
        Profesion toSave = profesionRepository.findbyId(profesion.getCodigo());
        if (toSave == null) {
            return null;
        }

        profesionRepository.update(profesion);

        return profesion;
    }

    @Override
    public Profesion eliminarProfesion(int id) {
        Profesion toDelete = profesionRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        profesionRepository.delete(id);
        return null;
    }

    @Override
    public Profesion buscarPorId(int id) {
        return profesionRepository.findbyId(id);
    }

    @Override
    public List<Profesion> buscarTodos() {
        return profesionRepository.findAll();
    }
}
