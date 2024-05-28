package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MunicipioRepository extends RepositoryWrapper<Integer, Municipio> {


    private final DepartamentoRepository departamentoRepository;
    private final PrioridadRepository prioridadRepository;

    public MunicipioRepository() {
        this.departamentoRepository = new DepartamentoRepository();
        this.prioridadRepository = new PrioridadRepository();
    }

    @Override
    public List<Municipio> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Municipio> municipios = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Municipio");
            while (rs.next()) {
                Municipio municipio = Municipio.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .poblacion(rs.getInt(3))
                        .prioridad(rs.getInt(4))
                        .departamento(rs.getInt(5))
                        .build();

                municipios.add(municipio);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return municipios;
    }

    @Override
    public Municipio findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Municipio municipio = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Municipio WHERE codigo = ?", integer);
            if (rs.next()) {
                municipio = Municipio.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .poblacion(rs.getInt(3))
                        .prioridad(rs.getInt(4))
                        .departamento(rs.getInt(5))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return municipio;
    }

    @Override
    public void save(Municipio municipio) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Municipio` (`Nombre`, `Poblacion`, `Prioridad`, `Departamento`) VALUES (?, ?, ?, ?)",
                    municipio.getNombre(),
                    municipio.getPoblacion(),
                    municipio.getPrioridad(),
                    municipio.getDepartamento()
            );
            if (rs == -1) {
                throw new RuntimeException("Municipio no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Municipio WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Municipio no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Municipio municipio) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Municipio t
                            SET t.Nombre = ?,
                                t.Poblacion  = ?,
                                t.Prioridad  = ?,
                                t.Departamento  = ?
                            WHERE t.Codigo = ?;
                            """,
                    municipio.getNombre(),
                    municipio.getPoblacion(),
                    municipio.getPrioridad(),
                    municipio.getDepartamento(),
                    municipio.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Municipio no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
