package com.ferreteria.demo.Repositories.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ferreteria", nullable = false)
    private Ferreteria ferreteria;
}