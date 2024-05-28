package application.banco.service;

import application.banco.model.Bitacora;

import java.util.List;

public interface IBitacoraService {

    public Bitacora crearBitacora(Bitacora bitacora);

    public Bitacora actualizarBitacora(Bitacora bitacora);

    public Bitacora eliminarBitacora(Integer id);

    public Bitacora buscarPorId(Integer id);

    public List<Bitacora> buscarTodos();
}
