package application.banco.repository;

import java.sql.*;

public abstract class RepositoryWrapper<T, V> implements IRepository<T, V> {
    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    private static final String DBNAME = "banco";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME + "?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    protected Connection conectar() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(CONTROLADOR);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage() +
                    ". >>> Error de Conexion 1!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() +
                    ". >>> Error de Conexion 2!!");
        }

        return connection;
    }

    protected void finalizarConexion(Connection conexion, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conexion != null) {
                conexion.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage() +
                    ". >>> Error de Desconexion!!");
        }

    }

    protected ResultSet ejecutarQuery(Connection conexion, String query, Object... params) {
        PreparedStatement pstmt = null;
        try {
            conexion = conectar();
            pstmt = conexion.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Integer modificarQuery(Connection conexion, String query, Object... params) {
        PreparedStatement pstmt = null;
        try {
            conexion = conectar();
            pstmt = conexion.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
