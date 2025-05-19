package com.ferreteria.demo.DTO.Ferreteria;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CrearFerreteriaDTO {

    @NotBlank(message = "El NIT no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String nit;

    @NotBlank(message = "La razón social no puede estar vacía")
    @Column(nullable = false)
    private String razonSocial;

    @NotBlank(message = "El representante no puede estar vacío")
    @Column(nullable = false)
    private String representante;
}
