package application.banco.model;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {
    private int codigo;
    private String nombre;
    private int poblacion;
}
