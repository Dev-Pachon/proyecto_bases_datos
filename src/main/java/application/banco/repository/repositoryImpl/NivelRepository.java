package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NivelRepository extends RepositoryWrapper<Integer, Nivel> {
    @Override
    public List<Nivel> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Nivel> niveles = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Nivel");
            while (rs.next()) {

                Nivel nivel = Nivel.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .build();

                niveles.add(nivel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return niveles;
    }

    @Override
    public Nivel findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Nivel nivel = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Nivel WHERE codigo = ?", integer);
            if (rs.next()) {
                nivel = Nivel.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return nivel;
    }

    @Override
    public void save(Nivel nivel) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Nivel` (`Nombre`) VALUES (?)",
                    nivel.getNombre()
            );
            if (rs == -1) {
                throw new RuntimeException("Nivel no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Nivel WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Nivel no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Nivel nivel) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Nivel t
                            SET t.Nombre = ?
                            WHERE t.Codigo = ?;
                            """,
                    nivel.getNombre(),
                    nivel.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Nivel no actualizado o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
