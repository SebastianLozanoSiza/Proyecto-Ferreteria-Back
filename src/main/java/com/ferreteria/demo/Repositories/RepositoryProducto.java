package com.ferreteria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Producto;

public interface RepositoryProducto extends CrudRepository<Producto,Long>{
    
    boolean existsByNombreProducto(String nombreProducto);

    boolean existsByCategoria(String categoria);
}
