package com.ferreteria.demo.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Credenciales;

public interface RepositoryCredenciales extends CrudRepository<Credenciales,Long>{
    
    Optional<Credenciales> findByNombreUsuario(String nombreUsuario);
}
