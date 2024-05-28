package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository extends RepositoryWrapper<Integer, Empleado> {

    private final UsuarioRepository usuarioRepository;
    private final ProfesionRepository profesionRepository;

    public EmpleadoRepository() {
        this.usuarioRepository = new UsuarioRepository();
        this.profesionRepository = new ProfesionRepository();
    }

    @Override
    public List<Empleado> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Empleado> empleados = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Empleado");
            while (rs.next()) {

                Empleado empleado = Empleado.builder()
                        .codigo(rs.getInt(1))
                        .cedula(rs.getString(2))
                        .nombre(rs.getString(3))
                        .direccion(rs.getString(4))
                        .telefono(rs.getString(5))
                        .usuario(rs.getInt(6))
                        .build();

                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return empleados.stream().peek(empleado -> {
            List<Profesion> profesiones = profesionRepository.findAllByEmpleadoId(empleado.getCodigo());
            empleado.setProfesiones(profesiones.stream().map(Profesion::getCodigo).toList());
        }).toList();
    }

    @Override
    public Empleado findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Empleado empleado = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Empleado WHERE codigo = ?", integer);
            if (rs.next()) {

                empleado = Empleado.builder()
                        .codigo(rs.getInt(1))
                        .cedula(rs.getString(2))
                        .nombre(rs.getString(3))
                        .direccion(rs.getString(4))
                        .telefono(rs.getString(5))
                        .usuario(rs.getInt(6))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        List<Profesion> profesiones = profesionRepository.findAllByEmpleadoId(empleado.getCodigo());
        empleado.setProfesiones(profesiones.stream().map(Profesion::getCodigo).toList());

        return empleado;
    }

    @Override
    public void save(Empleado empleado) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Empleado` (`Cedula`, `Nombre`, `DireccionResidencia`, `Telefono`, `Usuario`) VALUES (?, ?, ?, ?, ?)",
                    empleado.getCedula(),
                    empleado.getNombre(),
                    empleado.getDireccion(),
                    empleado.getTelefono(),
                    empleado.getUsuario());
            if (rs == -1) {
                throw new RuntimeException("Empleado no guardado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }

        empleado.setProfesiones(profesionRepository.findAllByEmpleadoId(empleado.getCodigo()).stream().map(Profesion::getCodigo).toList());
    }

    private void saveEmpleadoProfesion(Empleado empleado, Profesion profesion) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `DetalleEmpleadoProfesion` (`Empleado`, `Profesion`) VALUES (?, ?)",
                    empleado.getCodigo(),
                    profesion.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Relacion Empleado Profesion no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Empleado WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Empleado no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Empleado empleado) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Empleado t
                            SET t.Cedula = ?,
                                t.Nombre  = ?,
                                t.DireccionResidencia  = ?,
                                t.Telefono  = ?,
                                t.Usuario  = ?
                            WHERE t.Codigo = ?;
                            """,
                    empleado.getCedula(),
                    empleado.getNombre(),
                    empleado.getDireccion(),
                    empleado.getTelefono(),
                    empleado.getUsuario(),
                    empleado.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Empleado no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
