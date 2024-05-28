package application.banco.service;

import application.banco.model.Nivel;

import java.util.List;

public interface INivelService {

    public Nivel crearNivel(Nivel nivel);

    public Nivel actualizarNivel(Nivel nivel);

    public Nivel eliminarNivel(int id);

    public Nivel buscarPorId(int id);

    public List<Nivel> buscarTodos();

}
