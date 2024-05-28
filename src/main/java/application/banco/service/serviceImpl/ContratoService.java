package application.banco.service.serviceImpl;

import application.banco.model.Contrato;
import application.banco.repository.repositoryImpl.ContratoRepository;
import application.banco.service.IContratoService;

import java.util.List;

public class ContratoService implements IContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoService() {
        this.contratoRepository = new ContratoRepository();
    }

    @Override
    public Contrato crearContrato(Contrato contrato) {
        contratoRepository.save(contrato);
        return contrato;
    }

    @Override
    public Contrato actualizarContrato(Contrato contrato) {
        Contrato toSave = contratoRepository.findbyId(contrato.getCodigo());
        if (toSave == null) {
            return null;
        }

        contratoRepository.update(contrato);

        return contrato;
    }

    @Override
    public Contrato eliminarContrato(Integer id) {
        Contrato toDelete = contratoRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        contratoRepository.delete(id);
        return null;
    }

    @Override
    public Contrato buscarPorId(Integer id) {
        return contratoRepository.findbyId(id);
    }

    @Override
    public List<Contrato> buscarTodos() {
        return contratoRepository.findAll();
    }
}
