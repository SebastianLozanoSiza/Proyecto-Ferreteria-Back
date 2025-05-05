package com.ferreteria.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDTO {
    
    private boolean Error;
    private String Codigo;
    private String Descripcion;
}
