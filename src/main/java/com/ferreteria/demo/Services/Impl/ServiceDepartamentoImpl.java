package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.DepartamentoDTOConverter;
import com.ferreteria.demo.DTO.Departamento.DepartamentoDTO;
import com.ferreteria.demo.DTO.Departamento.ListarDepartamentoDTO;
import com.ferreteria.demo.Repositories.RepositoryDepartamento;
import com.ferreteria.demo.Repositories.Entities.Departamento;
import com.ferreteria.demo.Services.ServiceDepartamento;

@Service
public class ServiceDepartamentoImpl implements ServiceDepartamento {

    @Autowired
    private RepositoryDepartamento repositoryDepartamento;

    @Autowired
    private DepartamentoDTOConverter convert;

    @Override
    public ListarDepartamentoDTO findAll(String nombre) {
        Iterable<Departamento> departamentoIterable = repositoryDepartamento.findAllOrdered();
        List<Departamento> departamentos = StreamSupport.stream(departamentoIterable.spliterator(), false)
                .collect(Collectors.toList());
        if (nombre != null && !nombre.isEmpty()) {
            departamentos = departamentos.stream()
                    .filter(dep -> dep.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Si no hay coincidencias, devolver una lista vacía
        if (departamentos.isEmpty()) {
            return new ListarDepartamentoDTO(); // Lista vacía
        }
        
        return convert.convertListarToDTO(departamentos);

    }

    @Override
    public DepartamentoDTO save(DepartamentoDTO departamentoDTO) {
        if (repositoryDepartamento.existsByNombre(departamentoDTO.getNombre())) {
            throw new DataIntegrityViolationException("El nombre del departamento ya está en uso.");
        }

        Departamento departamento = convert.convertToEntity(departamentoDTO);
        departamento.setNombre(departamentoDTO.getNombre());
        Departamento savedDepartamento = repositoryDepartamento.save(departamento);
        return convert.convertToDTO(savedDepartamento);
    }

    @Override
    public DepartamentoDTO update(Long id, DepartamentoDTO departamentoDTO) {
        Optional<Departamento> departamentoCurrentOptional = repositoryDepartamento.findById(id);

        if (departamentoCurrentOptional.isPresent()) {
            Departamento departamentoCurrent = departamentoCurrentOptional.get();
            if (repositoryDepartamento.existsByNombre(departamentoDTO.getNombre()) &&
                    !departamentoCurrent.getNombre().equals(departamentoDTO.getNombre())) {
                throw new DataIntegrityViolationException("El nombre del departamento ya está en uso.");
            }
            departamentoCurrent.setNombre(departamentoDTO.getNombre());
            Departamento updatedDepartamento = repositoryDepartamento.save(departamentoCurrent);
            return convert.convertToDTO(updatedDepartamento);
        }
        throw new IllegalArgumentException("El departamento con ID " + id + " no existe.");
    }

    @Override
    public void delete(Long id) {
        Optional<Departamento> departamentOptional = repositoryDepartamento.findById(id);
        if (departamentOptional.isPresent()) {
            repositoryDepartamento.delete(departamentOptional.get());
        } else {
            throw new IllegalArgumentException("El departamento con ID " + id + " no existe.");
        }
    }

}
