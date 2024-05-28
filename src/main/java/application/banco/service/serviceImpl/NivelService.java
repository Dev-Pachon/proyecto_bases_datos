package application.banco.service.serviceImpl;

import application.banco.model.Nivel;
import application.banco.model.Nivel;
import application.banco.repository.repositoryImpl.NivelRepository;
import application.banco.service.INivelService;

import java.util.List;

public class NivelService implements INivelService {

    private final NivelRepository nivelRepository;

    public NivelService() {
        this.nivelRepository = new NivelRepository();
    }

    @Override
    public Nivel crearNivel(Nivel nivel) {
        nivelRepository.save(nivel);
        return nivel;
    }

    @Override
    public Nivel actualizarNivel(Nivel nivel) {
        Nivel toSave = nivelRepository.findbyId(nivel.getCodigo());
        if (toSave == null) {
            return null;
        }

        nivelRepository.update(nivel);

        return nivel;
    }

    @Override
    public Nivel eliminarNivel(int id) {
        Nivel toDelete = nivelRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        nivelRepository.delete(id);
        return null;
    }

    @Override
    public Nivel buscarPorId(int id) {
        return nivelRepository.findbyId(id);
    }

    @Override
    public List<Nivel> buscarTodos() {
        return nivelRepository.findAll();

    }
}
