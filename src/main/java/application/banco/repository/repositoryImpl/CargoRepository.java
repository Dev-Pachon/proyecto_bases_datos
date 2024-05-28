package application.banco.repository.repositoryImpl;

import application.banco.model.Bitacora;
import application.banco.model.Cargo;
import application.banco.model.Funcion;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CargoRepository extends RepositoryWrapper<Integer, Cargo> {

    private final FuncionRepository funcionRepository;

    public CargoRepository() {
        this.funcionRepository = new FuncionRepository();
    }

    @Override
    public List<Cargo> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Cargo> cargos = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Cargo");
            while (rs.next()) {

                List<Funcion> funciones = funcionRepository.findAllByCargoId(rs.getInt(1));

                Cargo cargo = Cargo.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .salario(rs.getDouble(3))
                        .funciones(funciones.stream().map(Funcion::getCodigo).toList())
                        .build();
                cargos.add(cargo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return cargos;
    }

    @Override
    public Cargo findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Cargo cargo = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Cargo WHERE codigo = ?", integer);
            if (rs.next()) {
                List<Funcion> funciones = funcionRepository.findAllByCargoId(rs.getInt(1));

                cargo = Cargo.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .salario(rs.getDouble(3))
                        .funciones(funciones.stream().map(Funcion::getCodigo).toList())
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return cargo;
    }

    @Override
    public void save(Cargo cargo) {
        Connection conexion = null;
        ResultSet rs = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "INSERT INTO `Cargo` (`Nombre`, `Salario`) VALUES (?, ?)",
                    cargo.getNombre(),
                    cargo.getSalario());
            if (!rs.next()) {
                throw new RuntimeException("Cargo no guardado");
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
            rs = ejecutarQuery(conexion, "DELETE FROM Cargo WHERE Codigo = ?", integer);
            if (!rs.next()) {
                throw new RuntimeException("Cargo no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }
    }

    @Override
    public void update(Cargo cargo) {
        Connection conexion = null;
        ResultSet rs = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "UPDATE Cargo t\n" +
                            "SET t.Nombre = '?',\n" +
                            "    t.Salario  = '?',\n" +
                            "WHERE t.Codigo = ?;\n",
                    cargo.getNombre(),
                    cargo.getSalario());
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
