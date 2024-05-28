package application.banco.service.serviceImpl;

import application.banco.model.Sucursal;
import application.banco.model.Sucursal;
import application.banco.repository.repositoryImpl.SucursalRepository;
import application.banco.service.ISucursalService;

import java.util.List;

public class SucursalService implements ISucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService() {
        this.sucursalRepository = new SucursalRepository();
    }

    @Override
    public Sucursal crearSucursal(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
        return sucursal;
    }

    @Override
    public Sucursal actualizarSucursal(Sucursal sucursal) {
        Sucursal toSave = sucursalRepository.findbyId(sucursal.getCodigo());
        if (toSave == null) {
            return null;
        }

        sucursalRepository.update(sucursal);

        return sucursal;
    }

    @Override
    public Sucursal eliminarSucursal(int id) {
        Sucursal toDelete = sucursalRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        sucursalRepository.delete(id);
        return null;
    }

    @Override
    public Sucursal buscarPorId(int id) {
        return sucursalRepository.findbyId(id);
    }

    @Override
    public List<Sucursal> buscarTodos() {
        return sucursalRepository.findAll();
    }
}
