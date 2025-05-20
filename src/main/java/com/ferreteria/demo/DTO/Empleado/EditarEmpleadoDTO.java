package com.ferreteria.demo.DTO.Empleado;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditarEmpleadoDTO {

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

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @NotNull(message = "El usuario no puede ser nulo")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @NotNull(message = "La contraseña no puede ser nula")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un caracter especial")
    private String contrasena;

    @NotNull(message = "El id del rol no puede ser nulo")
    @Column(nullable = false)
    private Long idRol;

    @NotNull(message = "El id de la ferreteria no puede ser nulo")
    @Column(nullable = false)
    private Long idFerreteria;
}
