package application.banco.service.serviceImpl;

import application.banco.model.Bitacora;
import application.banco.model.Usuario;
import application.banco.repository.repositoryImpl.BitacoraRepository;
import application.banco.service.IBitacoraService;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class BitacoraService implements IBitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraService() {
        this.bitacoraRepository = new BitacoraRepository();
    }

    @Override
    public Bitacora crearBitacora(Bitacora bitacora) {
        bitacoraRepository.save(bitacora);
        return bitacora;
    }

    @Override
    public Bitacora actualizarBitacora(Bitacora bitacora) {
        Bitacora toSave = bitacoraRepository.findbyId(bitacora.getCodigo());
        if (toSave == null) {
            return null;
        }

        bitacoraRepository.update(bitacora);

        return bitacora;
    }

    @Override
    public Bitacora eliminarBitacora(Integer id) {
        Bitacora toDelete = bitacoraRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        bitacoraRepository.delete(id);
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
