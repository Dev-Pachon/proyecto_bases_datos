package application.banco.service.serviceImpl;

import application.banco.model.Usuario;
import application.banco.service.IUsuarioService;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


public class UsuarioService implements IUsuarioService {

    @Override
    public Usuario inicioSesion(String username, String password) {
        return null;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        md5Hex(usuario.getClave());
        return null;
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario eliminar(int id) {
        return null;
    }

    @Override
    public Usuario buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Usuario> buscarTodos() {
        return List.of();
    }
}
