package com.ferreteria.demo.DTO.Empleado;

import lombok.Data;

@Data
public class EmpleadoDTO {
    
    private Long idEmpleado;
    private String identificacion;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;
    private String telefono;
    private String nombreUsuario;
    private Long idRol;
    private String nombreRol;
    private Long idFerreteria;
    private String razonSocial;
}
