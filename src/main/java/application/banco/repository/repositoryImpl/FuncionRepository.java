package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionRepository extends RepositoryWrapper<Integer, Funcion> {
    @Override
    public List<Funcion> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Funcion> funciones = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Funcion");
            while (rs.next()) {

                Funcion funcion = Funcion.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .descripcion(rs.getString(3))
                        .build();

                funciones.add(funcion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return funciones;
    }

    public List<Funcion> findAllByCargoId(Integer cargoid) {
        Connection conexion = null;
        ResultSet rs = null;

        List<Funcion> funciones = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT f.* FROM FuncionCargo fc, Funcion f WHERE fc.Cargo = ? AND fc.Funcion = f.Codigo", cargoid);
            while (rs.next()) {

                Funcion funcion = Funcion.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .descripcion(rs.getString(3))
                        .build();

                funciones.add(funcion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return funciones;
    }

    @Override
    public Funcion findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Funcion funcion = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Funcion WHERE codigo = ?", integer);
            if (rs.next()) {
                funcion = Funcion.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .descripcion(rs.getString(3))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return funcion;
    }

    @Override
    public void save(Funcion funcion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Funcion` (`Nombre`, `Description`) VALUES (?, ?)",
                    funcion.getNombre(),
                    funcion.getDescripcion());
            if (rs == -1) {
                throw new RuntimeException("Funcion no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Funcion WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Funcion no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Funcion funcion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Funcion t
                            SET t.Nombre = ?,
                                t.Descripcion  = ?
                            WHERE t.Codigo = ?;
                            """,
                    funcion.getNombre(),
                    funcion.getDescripcion(),
                    funcion.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Funcion no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
