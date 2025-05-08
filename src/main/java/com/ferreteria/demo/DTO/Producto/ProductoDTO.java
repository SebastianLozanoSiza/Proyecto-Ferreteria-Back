package com.ferreteria.demo.DTO.Producto;

import lombok.Data;

@Data
public class ProductoDTO {

    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private String categoria;
    private Double precio;
    private Integer stock;
    private String razonSocialFerreteria;
}
