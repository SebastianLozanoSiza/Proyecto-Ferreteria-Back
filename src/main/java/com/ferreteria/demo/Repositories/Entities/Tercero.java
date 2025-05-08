package com.ferreteria.demo.Repositories.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "terceros")
public class Tercero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTercero;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Column(nullable = false, unique = true, length = 50)
    private String identificacion;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Column(nullable = false)
    private String apellidos;

    @NotBlank(message = "El correo no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false)
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(nullable = false)
    private String telefono;
}
