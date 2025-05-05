package com.ferreteria.demo.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Empleado;
import com.ferreteria.demo.Repositories.Entities.Tercero;

public interface RepositoryEmpleado extends CrudRepository<Empleado, Long> {

    Optional<Empleado> findByTercero(Tercero tercero);
}
