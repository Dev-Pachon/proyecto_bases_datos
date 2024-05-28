package application.banco.service.serviceImpl;

import application.banco.model.Bitacora;
import application.banco.repository.repositoryImpl.BitacoraRepository;
import application.banco.service.IBitacoraService;

import java.util.List;

public class BitacoraService implements IBitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraService() {
        this.bitacoraRepository = new BitacoraRepository();
    }

    @Override
    public Bitacora crearBitacora(Bitacora bitacora) {
        return null;
    }

    @Override
    public Bitacora actualizarBitacora(Bitacora bitacora) {
        return null;
    }

    @Override
    public Bitacora eliminarBitacora(Integer id) {
        return null;
    }

    @Override
    public Bitacora buscarPorId(Integer id) {
        return bitacoraRepository.findbyId(id);
    }

    @Override
    public List<Bitacora> buscarTodos() {
        return bitacoraRepository.findAll();
    }
}
