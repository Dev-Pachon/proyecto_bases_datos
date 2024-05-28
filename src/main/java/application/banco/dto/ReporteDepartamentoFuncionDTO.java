package application.banco.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteDepartamentoFuncionDTO implements Reporte {
    private int funcionCodigo;
    private String nombreFuncion;
    private String descripcion;
    private String nombreDepartamento;
}
