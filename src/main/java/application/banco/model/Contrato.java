package application.banco.model;

import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {
    private int codigo;
    private int empleado;
    private int cargo;
    private int sucursal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
