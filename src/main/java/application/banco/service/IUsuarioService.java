package application.banco.service;

import application.banco.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    public Usuario inicioSesion(String username, String password);

    public Usuario registrar(Usuario usuario);

    public Usuario actualizar(Usuario usuario);

    public Usuario eliminar(int id);

    public Usuario buscarPorId(int id);

    public List<Usuario> buscarTodos();
}
