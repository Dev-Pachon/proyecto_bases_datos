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
public class Cargo {
    private int codigo;
    private String nombre;
    private Double salario;
    List<Integer> funciones;
}
