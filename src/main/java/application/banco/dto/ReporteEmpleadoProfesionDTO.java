package application.banco.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReporteEmpleadoProfesionDTO implements Reporte {
    private int empleadoCodigo;
    private String nombreEmpleado;
    private int profesionCodigo;
    private String nombreProfesion;
    private String descripcion;
}
