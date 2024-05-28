package application.banco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bitacora {
    private int codigo;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private int usuario;
}
