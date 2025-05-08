package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Producto.CrearProductoDTO;
import com.ferreteria.demo.DTO.Producto.ListarProductoDTO;

public interface ServiceProducto {
    
    ListarProductoDTO findAll(String nombreProducto, String categoria, String razonSocial);

    CrearProductoDTO save(CrearProductoDTO crearProductoDTO);

    CrearProductoDTO update(Long id, CrearProductoDTO crearProductoDTO);

    void delete(Long id);
}
