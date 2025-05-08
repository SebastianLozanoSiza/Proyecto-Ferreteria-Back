package com.ferreteria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Ferreteria;

public interface RepositoryFerreteria extends CrudRepository<Ferreteria, Long> {

}
