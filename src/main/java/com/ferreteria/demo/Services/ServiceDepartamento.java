package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Departamento.DepartamentoDTO;
import com.ferreteria.demo.DTO.Departamento.ListarDepartamentoDTO;

public interface ServiceDepartamento {
    
    ListarDepartamentoDTO findAll(String nombre);

    DepartamentoDTO save(DepartamentoDTO departamentoDTO);

    DepartamentoDTO update(Long id, DepartamentoDTO departamentoDTO);

    void delete(Long id);
}
