package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SucursalRepository extends RepositoryWrapper<Integer, Sucursal> {

    private final MunicipioRepository municipioRepository;
    private final EmpleadoRepository empleadoRepository;


    public SucursalRepository() {
        municipioRepository = new MunicipioRepository();
        empleadoRepository = new EmpleadoRepository();
    }

    @Override
    public List<Sucursal> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Sucursal> sucursales = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Sucursal");
            while (rs.next()) {

                Sucursal sucursal = Sucursal.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .presupuesto(rs.getDouble(3))
                        .municipio(rs.getInt(4))
                        .director(rs.getInt(5))
                        .build();

                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return sucursales;
    }

    @Override
    public Sucursal findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Sucursal sucursal = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Sucursal WHERE codigo = ?", integer);
            if (rs.next()) {

                sucursal = Sucursal.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .presupuesto(rs.getDouble(3))
                        .municipio(rs.getInt(4))
                        .director(rs.getInt(5))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return sucursal;
    }

    @Override
    public void save(Sucursal sucursal) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Sucursal` (`Nombre`, `Presupuesto`, `Municipio`, `Director`) VALUES (?, ?, ?, ?)",
                    sucursal.getNombre(),
                    sucursal.getPresupuesto(),
                    sucursal.getMunicipio(),
                    sucursal.getDirector()
            );
            if (rs == -1) {
                throw new RuntimeException("Sucursal no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Sucursal WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Sucursal no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Sucursal sucursal) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Sucursal t
                            SET t.Nombre = ?,
                                t.Presupuesto  = ?,
                                t.Municipio  = ?,
                                t.Director  = ?
                            WHERE t.Codigo = ?;
                            """,
                    sucursal.getNombre(),
                    sucursal.getPresupuesto(),
                    sucursal.getMunicipio(),
                    sucursal.getDirector(),
                    sucursal.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Sucursal no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
