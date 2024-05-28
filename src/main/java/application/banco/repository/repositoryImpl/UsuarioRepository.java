package application.banco.repository.repositoryImpl;

import application.banco.model.*;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository extends RepositoryWrapper<Integer, Usuario> {

    public final NivelRepository nivelRepository;

    public UsuarioRepository() {
        nivelRepository = new NivelRepository();
    }

    @Override
    public List<Usuario> findAll() {
        Connection conexion = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Usuario");
            while (rs.next()) {
                Usuario usuario = Usuario.builder()
                        .codigo(rs.getInt(1))
                        .nomUsuario(rs.getString(2))
                        .clave(rs.getString(3))
                        .fechaCreacion((LocalDateTime) rs.getObject(4))
                        .nivel(rs.getInt(5))
                        .build();

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return usuarios;
    }

    @Override
    public Usuario findbyId(Integer integer) {
        Connection conexion = null;
        ResultSet rs = null;

        Usuario usuario = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Usuario WHERE codigo = ?", integer);
            if (rs.next()) {

                usuario = Usuario.builder()
                        .codigo(rs.getInt(1))
                        .nomUsuario(rs.getString(2))
                        .clave(rs.getString(3))
                        .fechaCreacion((LocalDateTime) rs.getObject(4))
                        .nivel(rs.getInt(5))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return usuario;
    }

    public Usuario findbyNomUsuario(String nomUsuario) {
        Connection conexion = null;
        ResultSet rs = null;

        Usuario usuario = null;

        try {
            conexion = conectar();
            rs = ejecutarQuery(conexion, "SELECT * FROM Usuario WHERE NomUsuario = ?", nomUsuario);
            if (rs.next()) {
                usuario = Usuario.builder()
                        .codigo(rs.getInt(1))
                        .nomUsuario(rs.getString(2))
                        .clave(rs.getString(3))
                        .fechaCreacion((LocalDateTime) rs.getObject(4))
                        .nivel(rs.getInt(5))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, rs);
        }

        return usuario;
    }


    @Override
    public void save(Usuario usuario) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, "INSERT INTO `Usuario` (`NomUsuario`, `Clave`, `FechaCreacion`, `Nivel`) VALUES (?, ?, ?, ?)",
                    usuario.getNomUsuario(),
                    usuario.getClave(),
                    LocalDateTime.now(),
                    usuario.getNivel()
            );
            if (rs == -1) {
                throw new RuntimeException("Usuario no guardado");
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
            rs = modificarQuery(conexion, "DELETE FROM Usuario WHERE Codigo = ?", integer);
            if (rs == -1) {
                throw new RuntimeException("Usuario no eliminada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }

    @Override
    public void update(Usuario usuario) {
        Connection conexion = null;
        int rs = -1;

        try {
            conexion = conectar();
            rs = modificarQuery(conexion, """
                            UPDATE Usuario t
                            SET t.NomUsuario = ?,
                                t.Clave  = ?,
                                t.Nivel  = ?
                            WHERE t.Codigo = ?;
                            """,
                    usuario.getNomUsuario(),
                    usuario.getClave(),
                    usuario.getNivel(),
                    usuario.getCodigo());
            if (rs == -1) {
                throw new RuntimeException("Usuario no actualizada o no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            finalizarConexion(conexion, null, null);
        }
    }
}
