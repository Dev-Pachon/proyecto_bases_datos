package application.banco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int codigo;
    private String nomUsuario;
    private String clave;
    private LocalDateTime fechaCreacion;
    private int nivel;
}
