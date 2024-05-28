package application.banco.dto;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEmpleadoContratoDTO implements Reporte {
    private int empleadoCodigo;
    private String nombreEmpleado;
    private int contratoCodigo;
    private String cargo;
    private Date fechaInicio;
    private Date fechaFin;
    private float salario;
}
