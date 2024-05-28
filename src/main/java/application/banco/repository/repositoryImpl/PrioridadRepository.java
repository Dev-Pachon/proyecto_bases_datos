package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrioridadRepository extends RepositoryWrapper<Integer, Prioridad> {
    @Override
    public List<Prioridad> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Prioridad> prioridades = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Prioridad");
            while (rs.next()) {

                Prioridad prioridad = Prioridad.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .build();

                prioridades.add(prioridad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return prioridades;
    }

    @Override
    public Prioridad findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Prioridad prioridad = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Prioridad WHERE codigo = ?", integer);
            if (rs.next()) {
                prioridad = Prioridad.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return prioridad;
    }

    @Override
    public void save(Prioridad prioridad) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Prioridad` (`Nombre`) VALUES (?)",
                    prioridad.getNombre());
            if (rs == -1) {
                throw new RuntimeException("Prioridad no guardada");
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
            rs = modificarQuery(conexion, "DELETE FROM Prioridad WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Prioridad no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Prioridad prioridad) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Prioridad t
                            SET t.Nombre = ?
                            WHERE t.Codigo = ?;
                            """,
                    prioridad.getNombre(),
                    prioridad.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Prioridad no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
