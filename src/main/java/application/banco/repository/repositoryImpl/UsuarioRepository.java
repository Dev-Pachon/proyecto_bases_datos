package application.banco.repository.repositoryImpl;

import application.banco.model.Usuario;
import application.banco.repository.RepositoryWrapper;

import java.util.List;

public class UsuarioRepository extends RepositoryWrapper<Integer, Usuario> {
    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public Usuario findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Usuario usuario) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Usuario usuario) {
        
    }
}
