package com.ferreteria.demo.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Cliente;
import com.ferreteria.demo.Repositories.Entities.Tercero;

public interface RepositoryCliente extends CrudRepository<Cliente, Long> {
    Optional<Cliente> findByTercero(Tercero tercero);

}
