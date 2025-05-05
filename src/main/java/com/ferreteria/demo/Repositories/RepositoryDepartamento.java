package com.ferreteria.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ferreteria.demo.Repositories.Entities.Departamento;

public interface RepositoryDepartamento extends CrudRepository<Departamento, Long> {

    @Query("SELECT d FROM Departamento d ORDER BY d.idDepartamento ASC")
    List<Departamento> findAllOrdered();

    boolean existsByNombre(String nombreDepartamento);
}
