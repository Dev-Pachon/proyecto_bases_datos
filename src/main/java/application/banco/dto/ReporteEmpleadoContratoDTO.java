package application.banco.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ReporteEmpleadoContratoDTO {
    private int empleadoCodigo;
    private String nombreEmpleado;
    private int contratoCodigo;
    private String cargo;
    private Date fechaInicio;
    private Date fechaFin;
    private float salario;
}
