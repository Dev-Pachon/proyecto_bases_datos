package application.banco.service;

import application.banco.model.Municipio;

import java.util.List;

public interface IMunicipioService {

    public Municipio crearMunicipio(Municipio municipio);

    public Municipio actualizarMunicipio(Municipio municipio);

    public Municipio eliminarMunicipio(Integer id);

    public Municipio buscarMunicipio(Integer id);

    public List<Municipio> buscarTodos();

}
