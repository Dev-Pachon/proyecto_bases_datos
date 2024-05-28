package application.banco.repository.repositoryImpl;

import application.banco.dto.Reporte;
import application.banco.dto.ReporteEmpleadoContratoDTO;
import application.banco.error.CustomError;
import application.banco.model.Profesion;
import application.banco.repository.RepositoryWrapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportesRepository extends RepositoryWrapper<Integer, Reporte> {


    @Override
    public List<Reporte> findAll() {
        return null;
    }

    public List<Reporte> findAllbyID(Integer id) {
        Connection conexion = null;
        ResultSet rs = null;
        List<Reporte> reportes = new ArrayList<>();
        if (id == 1) {
            try {
                conexion = conectar();
                rs = ejecutarQuery(conexion, "SELECT e.Codigo AS EmpleadoCodigo, e.Nombre AS NombreEmpleado, c.Codigo AS ContratoCodigo, ca.Nombre AS Cargo, c.FechaInicio, c.FechaFin, ca.Salario\n" +
                        "FROM Empleado e\n" +
                        "INNER JOIN Contrato c ON e.Codigo = c.Empleado\n" +
                        "INNER JOIN Cargo ca ON c.Cargo = ca.Codigo;\n");
                while (rs.next()) {

                    reportes.add(new ReporteEmpleadoContratoDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), (Date) rs.getObject(5), (Date) rs.getObject(6), rs.getFloat(7)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                finalizarConexion(conexion, null, rs);
            }
        } else if (id == 2) {
        } else if (id == 3) {
        }
        return reportes;
    }

    @Override
    public Reporte findbyId(Integer integer) {
        return null;
    }

    @Override
    public void save(Reporte reporte) {

    }

    @Override
    public void delete(Integer integer) throws CustomError {

    }

    @Override
    public void update(Reporte reporte) {

    }
}
