package application.banco.repository.repositoryImpl;

import application.banco.model.Bitacora;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BitacoraRepository extends RepositoryWrapper<Integer, Bitacora> {

    @Override
    public List<Bitacora> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Bitacora> bitacoras = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Bitacora");
            while (rs.next()) {
                Bitacora bitacora = new Bitacora(rs.getInt(1), (LocalDateTime) rs.getObject(2), (LocalDateTime) rs.getObject(3), rs.getInt(4));
                bitacoras.add(bitacora);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return bitacoras;
    }

    @Override
    public Bitacora findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Bitacora bitacora = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Bitacora WHERE codigo = ?", integer);
            if (rs.next()) {
                bitacora = new Bitacora(rs.getInt(1), (LocalDateTime) rs.getObject(2), (LocalDateTime) rs.getObject(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return bitacora;
    }

    @Override
    public void save(Bitacora bitacora) {

        Connection conexion = null;
        ResultSet rs = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "INSERT INTO Bitacora (FechaIngreso, FechaSalida, Usuario) VALUES (?, ?, ?)",
                    bitacora.getUsuario(),
                    bitacora.getFechaIngreso(),
                    bitacora.getFechaSalida());
            if (!rs.next()) {
                throw new RuntimeException("Bitacora no guardada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "DELETE FROM Bitacora WHERE Codigo = ?", integer);
            if (!rs.next()) {
                throw new RuntimeException("Bitacora no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }
    }

    @Override
    public void update(Bitacora bitacora) {
        Connection conexion = null;
        ResultSet rs = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "UPDATE Bitacora t\n" +
                            "SET t.FechaIngreso = '?',\n" +
                            "    t.FechaSalida  = '?',\n" +
                            "    t.Usuario      = ?\n" +
                            "WHERE t.Codigo = ?;\n",
                    bitacora.getFechaIngreso(),
                    bitacora.getFechaSalida(),
                    bitacora.getUsuario()
            );
            if (!rs.next()) {
                throw new RuntimeException("Bitacora no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }
    }
}
