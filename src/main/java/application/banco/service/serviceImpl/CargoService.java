package application.banco.service.serviceImpl;

import application.banco.model.Cargo;
import application.banco.repository.repositoryImpl.CargoRepository;
import application.banco.service.ICargoService;

import java.util.List;

public class CargoService implements ICargoService {
    private final CargoRepository cargoRepository;

    public CargoService() {
        this.cargoRepository = new CargoRepository();
    }

    @Override
    public Cargo crearCargo(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargo;
    }

    @Override
    public Cargo actualizarCargo(Cargo cargo) {
        Cargo toSave = cargoRepository.findbyId(cargo.getCodigo());
        if (toSave == null) {
            return null;
        }

        cargoRepository.update(cargo);

        return cargo;
    }

    @Override
    public Cargo eliminarCargo(Integer id) {
        Cargo toDelete = cargoRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        cargoRepository.delete(id);
        return null;
    }

    @Override
    public Cargo buscarPorId(Integer id) {
        return cargoRepository.findbyId(id);
    }

    @Override
    public List<Cargo> buscarTodos() {
        return cargoRepository.findAll();
    }
}
