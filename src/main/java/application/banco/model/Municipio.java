package application.banco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {
    private int codigo;
    private String nombre;
    private int poblacion;
    private int prioridad;
    private int departamento;
}
