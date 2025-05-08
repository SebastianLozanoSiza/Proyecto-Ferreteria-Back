package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Producto.CrearProductoDTO;
import com.ferreteria.demo.DTO.Producto.ProductoDTO;
import com.ferreteria.demo.Repositories.Entities.Producto;

@Component
public class ProductoDTOConverter {

    private final ModelMapper dbm;

    public ProductoDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public CrearProductoDTO convertToDTO(Producto producto){
        CrearProductoDTO crearProductoDTO = dbm.map(producto, CrearProductoDTO.class);
        crearProductoDTO.setIdFerreteria(producto.getFerreteria().getIdFerreteria());
        return crearProductoDTO;
    }

    public Producto convertToEntity(CrearProductoDTO crearProductoDTO){
        Producto producto = dbm.map(crearProductoDTO, Producto.class);
        return producto;
    }

    public ProductoDTO convertToListarDTO(Producto producto) {
        ProductoDTO productoDTO = dbm.map(producto, ProductoDTO.class);
        productoDTO.setRazonSocialFerreteria(producto.getFerreteria().getRazonSocial());
        return productoDTO;
    }
}
