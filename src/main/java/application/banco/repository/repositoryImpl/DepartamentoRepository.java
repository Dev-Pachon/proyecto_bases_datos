package application.banco.repository.repositoryImpl;

import application.banco.error.CustomError;
import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepository extends RepositoryWrapper<Integer, Departamento> {
    @Override
    public List<Departamento> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Departamento> departamentos = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Departamento");
            while (rs.next()) {
                Departamento departamento = Departamento.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .poblacion(rs.getInt(3))
                        .build();

                departamentos.add(departamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return departamentos;
    }

    @Override
    public Departamento findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Departamento departamento = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Departamento WHERE codigo = ?", integer);
            if (rs.next()) {

                departamento = Departamento.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .poblacion(rs.getInt(3))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return departamento;
    }

    @Override
    public void save(Departamento departamento) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Departamento` (`Nombre`, `Poblacion`) VALUES (?, ?)",
                    departamento.getNombre(),
                    departamento.getPoblacion());
            if (rs == -1) {
                throw new RuntimeException("Departamento no guardado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void delete(Integer integer) throws CustomError {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "DELETE FROM Departamento WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Departamento no eliminado o no existe");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new CustomError("El departamento tiene municipios asociados");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Departamento departamento) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Departamento t
                            SET t.Nombre = ?,
                                t.Poblacion  = ?
                            WHERE t.Codigo = ?;
                            """,
                    departamento.getNombre(),
                    departamento.getPoblacion(),
                    departamento.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Departamento no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
