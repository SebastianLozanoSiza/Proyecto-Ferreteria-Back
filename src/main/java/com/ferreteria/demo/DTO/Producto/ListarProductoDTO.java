package com.ferreteria.demo.DTO.Producto;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class ListarProductoDTO {
    private RespuestaDTO respuesta;
    private List<ProductoDTO> productos;
}
