package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfesionRepository extends RepositoryWrapper<Integer, Profesion> {
    @Override
    public List<Profesion> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Profesion> profesiones = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Profesion");
            while (rs.next()) {

                Profesion profesion = Profesion.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .descripcion(rs.getString(3))
                        .build();

                profesiones.add(profesion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return profesiones;
    }

    @Override
    public Profesion findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Profesion profesion = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Profesion WHERE codigo = ?", integer);
            if (rs.next()) {
                profesion = Profesion.builder()
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

        return profesion;
    }

    public List<Profesion> findAllByEmpleadoId(Integer empleadoId) {
        Connection conexion = null;
        ResultSet rs = null;

        List<Profesion> profesiones = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT p.* FROM DetalleEmpleadoProfesion ep, Profesion p WHERE ep.Empleado = ? AND ep.Profesion = p.Codigo", empleadoId);
            while (rs.next()) {

                Profesion funcion = Profesion.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .descripcion(rs.getString(3))
                        .build();

                profesiones.add(funcion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return profesiones;
    }

    @Override
    public void save(Profesion profesion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Profesion` (`Nombre`, `Descripcion`) VALUES (?, ?)",
                    profesion.getNombre(),
                    profesion.getDescripcion()
            );
            if (rs == -1) {
                throw new RuntimeException("Profesion no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Profesion WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Profesion no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Profesion profesion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Profesion t
                            SET t.Nombre = ?,
                                t.Descripcion  = ?
                            WHERE t.Codigo = ?;
                            """,
                    profesion.getNombre(),
                    profesion.getDescripcion(),
                    profesion.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Profesion no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
