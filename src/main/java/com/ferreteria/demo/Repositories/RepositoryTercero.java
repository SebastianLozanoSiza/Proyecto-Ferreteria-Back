package com.ferreteria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Tercero;

public interface RepositoryTercero extends CrudRepository<Tercero, Long> {

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);
}
