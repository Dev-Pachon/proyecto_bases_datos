package application.banco.repository.repositoryImpl;

import application.banco.model.Contrato;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class ContratoRepository extends RepositoryWrapper<Integer, Contrato> {
    @Override
    public List<Contrato> findAll() {
        return List.of();
    }

    @Override
    public Contrato findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Contrato contrato) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Contrato contrato) {

    }
}
