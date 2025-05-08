package com.ferreteria.demo.DTO.Empleado;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConvertirClienteAEmpleadoDTO {
    
    @NotNull(message = "El id del cliente (tercero) es obligatorio")
    private Long idTercero;

    @NotNull(message = "El id del rol es obligatorio")
    private Long idRol;

    @NotNull(message = "El id de la ferretería es obligatorio")
    private Long idFerreteria; 
}
