package application.banco.service.serviceImpl;

import application.banco.model.Usuario;
import application.banco.repository.repositoryImpl.UsuarioRepository;
import application.banco.service.IUsuarioService;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    @Override
    public Usuario inicioSesion(String username, String password) {

        Usuario usuario = usuarioRepository.findbyNomUsuario(username);
        if (usuario == null) {
            return null;
        }

        if (!usuario.getClave().equals(md5Hex(password))) {
            return null;
        }

        return usuario;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        Usuario toSave = usuarioRepository.findbyNomUsuario(usuario.getNomUsuario());
        if (toSave != null) {
            return null;
        }
        toSave = Usuario.builder()
                .nomUsuario(usuario.getNomUsuario())
                .clave(md5Hex(usuario.getClave()))
                .nivel(usuario.getNivel())
                .build();
        usuarioRepository.save(toSave);
        return null;
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        Usuario toSave = usuarioRepository.findbyNomUsuario(usuario.getNomUsuario());
        if (toSave == null) {
            return null;
        }

        usuarioRepository.update(usuario);

        return usuario;
    }

    @Override
    public Usuario eliminar(int id) {
        Usuario toDelete = usuarioRepository.findbyId(id);
        if (toDelete == null) {
            return null;
        }
        usuarioRepository.delete(id);
        return null;
    }

    @Override
    public Usuario buscarPorId(int id) {
        return usuarioRepository.findbyId(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
