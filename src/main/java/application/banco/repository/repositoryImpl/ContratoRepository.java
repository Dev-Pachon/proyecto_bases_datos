package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContratoRepository extends RepositoryWrapper<Integer, Contrato> {

    private final EmpleadoRepository empleadoRepository;
    private final CargoRepository cargoRepository;
    private final SucursalRepository sucursalRepository;

    public ContratoRepository() {
        this.empleadoRepository = new EmpleadoRepository();
        this.cargoRepository = new CargoRepository();
        this.sucursalRepository = new SucursalRepository();
    }

    @Override
    public List<Contrato> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Contrato> contratos = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Contrato");
            while (rs.next()) {

                Contrato contrato = Contrato.builder()
                        .codigo(rs.getInt(1))
                        .empleado(rs.getInt(2))
                        .cargo(rs.getInt(3))
                        .sucursal(rs.getInt(4))
                        .fechaInicio((Date) rs.getObject(5))
                        .fechaFin((Date) rs.getObject(6))
                        .build();

                contratos.add(contrato);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return contratos;
    }

    @Override
    public Contrato findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Contrato contrato = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Contrato WHERE codigo = ?", integer);
            if (rs.next()) {
                contrato = Contrato.builder()
                        .codigo(rs.getInt(1))
                        .empleado(rs.getInt(2))
                        .cargo(rs.getInt(3))
                        .sucursal(rs.getInt(4))
                        .fechaInicio((Date) rs.getObject(5))
                        .fechaFin((Date) rs.getObject(6))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return contrato;
    }

    @Override
    public void save(Contrato contrato) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Contrato` (`Empleado`, `Cargo`, `Sucursal`, `FechaInicio`, `FechaFin`) VALUES (?, ?, ?, ?, ?)",
                    contrato.getEmpleado(),
                    contrato.getCargo(),
                    contrato.getSucursal(),
                    contrato.getFechaInicio(),
                    contrato.getFechaFin());
            if (rs == -1) {
                throw new RuntimeException("Contrato no guardado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "DELETE FROM Contrato WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Contrato no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Contrato contrato) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Contrato t
                            SET t.Empleado = ?,
                                t.Cargo  = ?,
                                t.Sucursal  = ?,
                                t.FechaInicio  = ?,
                                t.FechaFin  = ?
                            WHERE t.Codigo = ?;
                            """,
                    contrato.getEmpleado(),
                    contrato.getCargo(),
                    contrato.getSucursal(),
                    contrato.getFechaInicio(),
                    contrato.getFechaFin(),
                    contrato.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Contrato no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
