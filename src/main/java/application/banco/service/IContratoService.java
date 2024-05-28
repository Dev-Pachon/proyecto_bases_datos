package application.banco.service;

import application.banco.model.Contrato;

import java.util.List;

public interface IContratoService {

    public Contrato crearContrato(Contrato contrato);

    public Contrato actualizarContrato(Contrato contrato);

    public Contrato eliminarContrato(Integer id);

    public Contrato buscarPorId(Integer id);

    public List<Contrato> buscarTodos();

}
