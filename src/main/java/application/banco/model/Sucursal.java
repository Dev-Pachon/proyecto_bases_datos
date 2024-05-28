package application.banco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    private int codigo;
    private String nombre;
    private Double presupuesto;
    private int municipio;
    private int director;
}
