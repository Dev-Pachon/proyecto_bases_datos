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


                Cargo cargo = Cargo.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .salario(rs.getDouble(3))
                        .build();
                cargos.add(cargo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return cargos.stream().peek(cargo -> {
            List<Funcion> funciones = funcionRepository.findAllByCargoId(cargo.getCodigo());
            cargo.setFunciones(funciones.stream().map(Funcion::getCodigo).toList());
        }).collect(Collectors.toList());
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

                cargo = Cargo.builder()
                        .codigo(rs.getInt(1))
                        .nombre(rs.getString(2))
                        .salario(rs.getDouble(3))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        List<Funcion> funciones = funcionRepository.findAllByCargoId(cargo.getCodigo());
        cargo.setFunciones(funciones.stream().map(Funcion::getCodigo).toList());

        return cargo;
    }

    @Override
    public void save(Cargo cargo) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Cargo` (`Nombre`, `Salario`) VALUES (?, ?)",
                    cargo.getNombre(),
                    cargo.getSalario());

            if (rs == -1) {
                throw new RuntimeException("Cargo no guardado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
        cargo.getFunciones().forEach(e -> {
            Funcion funcion = funcionRepository.findbyId(e);
            saveCargoFuncion(cargo, funcion);
        });
    }

    private void saveCargoFuncion(Cargo cargo, Funcion funcion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `FuncionCargo` (`Funcion`, `Cargo`) VALUES (?, ?)",
                    cargo.getCodigo(),
                    funcion.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Relacion Funcion Cargo no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Cargo WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Cargo no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    public void deleteCargoFuncion(Integer funcion, Integer cargo) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "DELETE FROM FuncionCargo WHERE Funcion = ? AND Cargo = ?", funcion, cargo);
            if (rs == -1) {
                throw new RuntimeException("Cargo no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Cargo cargo) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Cargo t
                            SET t.Nombre = ?,
                                t.Salario  = ?,
                            WHERE t.Codigo = ?;;
                            """,
                    cargo.getNombre(),
                    cargo.getSalario(),
                    cargo.getCodigo());

            if (rs == -1) {
                throw new RuntimeException("Cargo no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
