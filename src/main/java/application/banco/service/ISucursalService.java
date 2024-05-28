package application.banco.service;

import application.banco.model.Sucursal;

import java.util.List;

public interface ISucursalService {
    public Sucursal crearSucursal(Sucursal sucursal);

    public Sucursal actualizarSucursal(Sucursal sucursal);

    public Sucursal eliminarSucursal(int id);

    public Sucursal buscarPorId(int id);

    public List<Sucursal> buscarTodos();
}
