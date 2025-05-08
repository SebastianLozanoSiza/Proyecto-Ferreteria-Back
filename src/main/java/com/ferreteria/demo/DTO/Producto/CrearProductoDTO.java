package com.ferreteria.demo.DTO.Producto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearProductoDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String nombreProducto;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "La categoría no puede estar vacía")
    @Column(nullable = false, unique = true)
    private String categoria;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "El id de la ferreteria no puede ser nulo")
    @Column(nullable = false)
    private Long idFerreteria;
}
