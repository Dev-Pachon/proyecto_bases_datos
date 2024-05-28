package application.banco.service.serviceImpl;

import application.banco.model.Municipio;
import application.banco.model.Municipio;
import application.banco.repository.repositoryImpl.MunicipioRepository;
import application.banco.service.IMunicipioService;

import java.util.List;

public class MunicipioService implements IMunicipioService {

    private final MunicipioRepository municipioRepository;

    public MunicipioService(){
        municipioRepository = new MunicipioRepository();
    }

    @Override
    public Municipio crearMunicipio(Municipio municipio) {
        municipioRepository.save(municipio);
        return municipio;
    }

    @Override
    public Municipio actualizarMunicipio(Municipio municipio) {
        Municipio toSave = municipioRepository.findbyId(municipio.getCodigo());
        if (toSave == null) {
            return null;
        }

        municipioRepository.update(municipio);

        return municipio;
    }

    @Override
    public Municipio eliminarMunicipio(Integer id) {
        Municipio toDelete = municipioRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        municipioRepository.delete(id);
        return null;
    }

    @Override
    public Municipio buscarMunicipio(Integer id) {
        return municipioRepository.findbyId(id);
    }

    @Override
    public List<Municipio> buscarTodos() {
        return municipioRepository.findAll();
    }
}
