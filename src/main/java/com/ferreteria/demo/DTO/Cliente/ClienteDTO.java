package com.ferreteria.demo.DTO.Cliente;

import lombok.Data;

@Data
public class ClienteDTO {
    
    private Long idCliente;
    private String identificacion;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;
    private String telefono;
}
