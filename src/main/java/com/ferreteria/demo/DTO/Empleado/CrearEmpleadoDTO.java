package com.ferreteria.demo.DTO.Empleado;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CrearEmpleadoDTO {

    @NotBlank(message = "El número de identificación es obligatorio")
    private String identificacion;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String correo;

    private String direccion;

    @Pattern(regexp = "^[0-9]{7,10}$", message = "El teléfono debe tener entre 7 y 10 dígitos")
    private String telefono;

    @NotNull(message = "El id del rol no puede ser nulo")
    @Column(nullable = false)
    private Long idRol;

    @NotNull(message = "El id de la ferreteria no puede ser nulo")
    @Column(nullable = false)
    private Long idFerreteria;
}
