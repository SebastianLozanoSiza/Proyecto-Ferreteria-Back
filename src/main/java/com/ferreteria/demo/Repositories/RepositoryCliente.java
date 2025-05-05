package com.ferreteria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Cliente;

public interface RepositoryCliente extends CrudRepository<Cliente,Long>{
    
}
