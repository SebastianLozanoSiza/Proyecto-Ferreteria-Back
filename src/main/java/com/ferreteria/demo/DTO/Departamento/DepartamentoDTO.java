package com.ferreteria.demo.DTO.Departamento;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartamentoDTO {

    @NotNull(message = "El nombre del departamento no puede ser nulo")
    @NotBlank(message = "El nombre del departamento no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String nombre;
}
