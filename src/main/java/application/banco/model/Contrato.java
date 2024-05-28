package application.banco.model;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {
    private int codigo;
    private int empleado;
    private int cargo;
    private int sucursal;
    private Date fechaInicio;
    private Date fechaFin;
}
