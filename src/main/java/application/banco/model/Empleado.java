package application.banco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    private int codigo;
    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;
    private int usuario;
    List<Integer> profesiones;
}
